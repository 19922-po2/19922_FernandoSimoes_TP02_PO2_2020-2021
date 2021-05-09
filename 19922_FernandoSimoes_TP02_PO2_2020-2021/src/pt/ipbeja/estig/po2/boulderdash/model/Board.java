package pt.ipbeja.estig.po2.boulderdash.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Fernando Simões nº 19922
 */

public class Board {
    private int nLine;
    private int nCol;
    private int score;
    private int nDiamonds;
    private int nGates;
    private Rockford rockford;
    private AbstractPosition[][] board;
    private View view;
    private Gate gate;
    private List<Diamond> diamondList;
    private List<Rock> rockList;
    private int endLvl = 0;

    public Board(String mapFile) throws IOException {
        this.score = 0;
        this.nDiamonds = 0;
        this.diamondList = new ArrayList<Diamond>();
        this.board = createBoard(mapFile);
        System.out.println(this.rockford.getLine()+"-"+this.rockford.getCol());
    }

    public void rockfordMoveUp() { //line-1
        if(this.rockford.getLine() - 1 >= 0 &&
                this.board[this.rockford.getLine() - 1][this.rockford.getCol()].possibleMoveTo()
                && this.rockford.getLine() > 0 && this.rockford.getLine() < this.nLine){
            //swaps rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //trigger
            triggerUp();
            //swaps target with rockford
            this.board[this.rockford.getLine() - 1][this.rockford.getCol()] = this.rockford;
            this.rockford.setLine(this.rockford.getLine() - 1);
            //refresh view
            this.view.rockfordMoved(this.rockford, this.board[this.rockford.getLine() + 1][this.rockford.getCol()]);
            checkWin();
        }
    }

    public void rockfordMoveDown() { //line+1
        if(this.rockford.getLine() + 1 < nLine &&
                this.board[this.rockford.getLine() + 1][this.rockford.getCol()].possibleMoveTo()
                && this.rockford.getLine() >= 0 && this.rockford.getLine() < this.nLine){
            //swaps rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //trigger
            triggerDown();
            //swaps target with rockford
            this.board[this.rockford.getLine() + 1][this.rockford.getCol()] = this.rockford;
            this.rockford.setLine(this.rockford.getLine() + 1);
            //refresh view
            this.view.rockfordMoved(this.rockford, this.board[this.rockford.getLine() - 1][this.rockford.getCol()]);
            checkWin();
        }
    }

    public void rockfordMoveRight(){ //col+1
        if(this.rockford.getCol() + 1 < nCol &&
                this.board[this.rockford.getLine()][this.rockford.getCol() + 1].possibleMoveTo()
                && this.rockford.getCol() >= 0 && this.rockford.getCol() < this.nCol){
            //swaps rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //trigger
            triggerRight();
            //swaps target with rockford
            this.board[this.rockford.getLine()][this.rockford.getCol() + 1] = this.rockford;
            this.rockford.setCol(this.rockford.getCol() + 1);
            //refresh view
            this.view.rockfordMoved(this.rockford, this.board[this.rockford.getLine()][this.rockford.getCol() - 1]);
            checkWin();
        }
    }

    public void rockfordMoveLeft() { //col-1
        if(this.rockford.getCol() - 1 >= 0 &&
                this.board[this.rockford.getLine()][this.rockford.getCol() - 1].possibleMoveTo()
                && this.rockford.getCol() > 0 && this.rockford.getCol() < this.nCol){
            //swaps rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //trigger
            triggerLeft();
            //swaps target with rockford
            this.board[this.rockford.getLine()][this.rockford.getCol() - 1] = this.rockford;
            this.rockford.setCol(this.rockford.getCol() - 1);
            //refresh view
            this.view.rockfordMoved(this.rockford, this.board[this.rockford.getLine()][this.rockford.getCol() + 1]);
            checkWin();
        }
    }

    private void triggerUp() {
        int points = this.board[this.rockford.getLine() - 1][this.rockford.getCol()].increaseScore();
        triggerScore(points);
        if(points > 0) {removeDiamond(this.board[this.rockford.getLine() - 1][this.rockford.getCol()]);}
        triggerDiamondFall();

    }
    private void triggerDown() {
        int points = this.board[this.rockford.getLine() + 1][this.rockford.getCol()].increaseScore();
        triggerScore(points);
        if(points > 0) {removeDiamond(this.board[this.rockford.getLine() + 1][this.rockford.getCol()]);}
        triggerDiamondFall();
    }
    private void triggerRight() {
        int points = this.board[this.rockford.getLine()][this.rockford.getCol() + 1].increaseScore();
        triggerScore(points);
        if(points > 0) {removeDiamond(this.board[this.rockford.getLine()][this.rockford.getCol() + 1]);}
        triggerDiamondFall();
    }
    private void triggerLeft() {
        int points = this.board[this.rockford.getLine()][this.rockford.getCol() - 1].increaseScore();
        triggerScore(points);
        if(points > 0) {removeDiamond(this.board[this.rockford.getLine()][this.rockford.getCol() - 1]);}
        triggerDiamondFall();
    }

    private void triggerScore(int points){
        if(points > 0) nDiamonds--;
        checkDiamondCount();
        this.score += points;
    }

    private void triggerDiamondFall(){
        for(Diamond diamond : diamondList){
            if(diamond.getLine() + 1 < nLine && this.board[diamond.getLine() + 1][diamond.getCol()].canReceiveFallingObject()){
                this.board[diamond.getLine()][diamond.getCol()] = new FreeTunnel(diamond.getLine(), diamond.getCol());
                diamond.setLine(diamond.getLine() + 1);
                this.board[diamond.getLine()][diamond.getCol()] = diamond;
                this.view.diamondMoved(diamond, this.board[diamond.getLine() - 1][diamond.getCol()]);
            }
        }
    }

    private void removeDiamond(AbstractPosition fallingObject){
        diamondList.removeIf(diamond -> diamond.equals(fallingObject));
    }

    private void checkDiamondCount(){
        if(nDiamonds == 0){
            board[this.gate.getLine()][this.gate.getCol()] = new Gate(this.gate.getLine(), this.gate.getCol());
            nGates = 1;
            this.view.gateAppeared(this.gate);
        }
    }

    private void checkWin(){
        if(nGates == 1 && this.gate.getLine() == this.rockford.getLine() && this.gate.getCol() == this.rockford.getCol()){
            this.view.gameWon(this.score);
            this.endLvl = 1;
            //TODO reset board after win and fix createMap()
        }
    }

    public AbstractPosition[][] createBoard(String mapFile) throws IOException {
        FileInputStream map = new FileInputStream(mapFile);
        Scanner scan = new Scanner(map);
        this.nLine = scan.nextInt();
        this.nCol = scan.nextInt();
        AbstractPosition[][] board = new AbstractPosition[nLine][nCol];

        String tmp;
        for(int i = 0; i < nLine; i++) {
            tmp = scan.next();
            for (int j = 0; j < nCol; j++) {
                switch (tmp.charAt(j)) {
                    case 'W':
                        board[i][j] = new Wall(i, j);
                        break;
                    case 'O':
                        board[i][j] = new OccupiedTunnel(i, j);
                        break;
                    case 'L':
                        board[i][j] = new FreeTunnel(i, j);
                        break;
                    case 'P':
                        board[i][j] = new Rock(i, j);
                        break;
                }
            }
        }
        tmp = scan.next();
        int tmpLine = scan.nextInt();
        int tmpCol = scan.nextInt();
        if(tmp.equals("J")){
            this.rockford = new Rockford(tmpLine, tmpCol);
            board[tmpLine][tmpCol] = this.rockford;
            scan.nextLine();
        }
        tmp = scan.next();
        tmpLine = scan.nextInt();
        tmpCol = scan.nextInt();
        if(tmp.equals("D")){
            board[tmpLine][tmpCol] = new Diamond(tmpLine, tmpCol);
            this.diamondList.add((Diamond) board[tmpLine][tmpCol]); //saves diamonds in list
            this.nDiamonds++;
            scan.nextLine();
        }
        tmp = scan.next();
        tmpLine = scan.nextInt();
        tmpCol = scan.nextInt();
        if(tmp.equals("G")){
            this.gate = new Gate(tmpLine, tmpCol);
            scan.nextLine();
        }
        return board;
    }

    public int getScore() {
        return this.score;
    }

    public Rockford getRockford() {
        return this.rockford;
    }

    public List<Diamond> getDiamondList(){
        return this.diamondList;
    }

    public int getnLine() {
        return nLine;
    }

    public int getnCol() {
        return nCol;
    }

    public int getEndLvl(){
        return  this.endLvl;
    }

    public AbstractPosition getEntity(int line, int col){
        return this.board[line][col];
    }

    public void setView(View view) {
        this.view = view;
    }

    public void rockfordGoTo(int line , int col){
        if(this.board[line][col].possibleMoveTo()){
            //swap rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //triggers
            int points = this.board[line][col].increaseScore();
            if(points > 0) nDiamonds--;
            if(nDiamonds == 0){
                board[this.gate.getLine()][this.gate.getCol()] = new Gate(this.gate.getLine(), this.gate.getCol());
                nGates = 1;
            }
            this.score += points;
            for(Diamond diamond : diamondList){
                if(diamond.getLine() + 1 < nLine && this.board[diamond.getLine() + 1][diamond.getCol()].canReceiveFallingObject()){
                    this.board[diamond.getLine()][diamond.getCol()] = new FreeTunnel(diamond.getLine(), diamond.getCol());
                    diamond.setLine(diamond.getLine() + 1);
                    this.board[diamond.getLine()][diamond.getCol()] = diamond;
                }
            }
            //swaps target with rockford
            this.board[line][col] = this.rockford;
            this.rockford.setLine(line);
            this.rockford.setCol(col);
            //checks win
            if(nGates == 1 && this.gate.getLine() == this.rockford.getLine() && this.gate.getCol() == this.rockford.getCol()){
                this.endLvl = 1;
            }
        }
    }
}

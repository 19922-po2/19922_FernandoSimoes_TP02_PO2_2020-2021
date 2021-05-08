package pt.ipbeja.estig.po2.boulderdash.model;


import java.io.*;
import java.util.Scanner;

public class Board {
    private int nLine;
    private int nCol;
    private int score;
    private Rockford rockford;
    private AbstractPosition[][] board;
    private View view;

    public Board(String mapFile) throws IOException {
        this.board = createBoard(mapFile);
        this.score = 0;
        printMap();
        System.out.println(this.rockford.getLine()+"-"+this.rockford.getCol());

        int endGame = 0;
        Scanner myobj = new Scanner(System.in);
        /*while(endGame == 0){
            System.out.println("Jogada:");
            String input = myobj.nextLine();
            if(input.equals("w")) rockfordMoveUp();
            if(input.equals("s")) rockfordMoveDown();
            if(input.equals("a")) rockfordMoveLeft();
            if(input.equals("d")) rockfordMoveRight();
            System.out.println("SCORE["+this.score+"]");
            printMap();
        }*/
    }

    public void rockfordMoveUp() { //line-1
        if(this.rockford.getLine() - 1 >= 0 &&
                this.board[this.rockford.getLine() - 1][this.rockford.getCol()].possibleMoveTo()
                && this.rockford.getLine() > 0 && this.rockford.getLine() < this.nLine){
            System.out.println("up");
            //swaps rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //triger TODO
            //this.board[this.rockford.getLine() - 1][this.rockford.getCol()].moveTrigger();
            //swaps target with rockford
            this.board[this.rockford.getLine() - 1][this.rockford.getCol()] = this.rockford;
            this.rockford.setLine(this.rockford.getLine() - 1);
        }
    }

    public void rockfordMoveDown() { //line+1
        if(this.rockford.getLine() + 1 < nLine &&
                this.board[this.rockford.getLine() + 1][this.rockford.getCol()].possibleMoveTo()
                && this.rockford.getLine() >= 0 && this.rockford.getLine() < this.nLine){
            System.out.println("down");
            //swaps rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //triger TODO
            //this.board[this.rockford.getLine() + 1][this.rockford.getCol()].moveTrigger();
            //swaps target with rockford
            this.board[this.rockford.getLine() + 1][this.rockford.getCol()] = this.rockford;
            this.rockford.setLine(this.rockford.getLine() + 1);
        }
    }

    public void rockfordMoveRight(){ //col+1
        if(this.rockford.getCol() + 1 < nCol &&
                this.board[this.rockford.getLine()][this.rockford.getCol() + 1].possibleMoveTo()
                && this.rockford.getCol() >= 0 && this.rockford.getCol() < this.nCol){
            System.out.println("right");
            //swaps rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //triger TODO
            //this.board[this.rockford.getLine()][this.rockford.getCol() + 1].moveTrigger();
            trigger();
            //swaps target with rockford
            this.board[this.rockford.getLine()][this.rockford.getCol() + 1] = this.rockford;
            this.rockford.setCol(this.rockford.getCol() + 1);
        }
    }

    private void trigger() {
        int points = this.board[this.rockford.getLine()][this.rockford.getCol() + 1].increaseScore();
        this.score += points;
        this.board[this.rockford.getLine()][this.rockford.getCol() + 1] = this.board[this.rockford.getLine()][this.rockford.getCol() + 1].moveTrigger();

    }

    public void rockfordMoveLeft() { //col-1
        if(this.rockford.getCol() - 1 >= 0 &&
                this.board[this.rockford.getLine()][this.rockford.getCol() - 1].possibleMoveTo()
                && this.rockford.getCol() > 0 && this.rockford.getCol() < this.nCol){
            System.out.println("left");
            //swaps rockford with free tunnel
            this.board[this.rockford.getLine()][this.rockford.getCol()] = new FreeTunnel(this.rockford.getLine(), this.rockford.getCol());
            //triger TODO
            //this.board[this.rockford.getLine()][this.rockford.getCol() - 1].moveTrigger();
            //swaps target with rockford
            this.board[this.rockford.getLine()][this.rockford.getCol() - 1] = this.rockford;
            this.rockford.setCol(this.rockford.getCol() - 1);
        }
    }

    public AbstractPosition[][] createBoard(String mapFile) throws IOException {
        FileInputStream map = new FileInputStream(mapFile);
        Scanner scan = new Scanner(map);
        this.nLine = scan.nextInt();
        this.nCol = scan.nextInt();
        System.out.println("line:" + nLine + " |Col:" + nCol);
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
            System.out.println("PLAYER: " + tmpLine + "-" + tmpCol);
        }
        tmp = scan.next();
        tmpLine = scan.nextInt();
        tmpCol = scan.nextInt();
        if(tmp.equals("D")){
            board[tmpLine][tmpCol] = new Diamond(tmpLine, tmpCol);
            scan.nextLine();
            System.out.println("Diamond: " + tmpLine + "-" + tmpCol);
        }
        return board;
    }

    public int getScore() {
        return this.score;
    }

    public Rockford getRockford() {
        return this.rockford;
    }

    public void printMap() {
        //System.out.println("NEW MAP");
        for (int i = 0; i < nLine; i++) {
            for (int j = 0; j < nCol; j++) {
                board[i][j].print();
            }
            System.out.println();
        }
    }
}

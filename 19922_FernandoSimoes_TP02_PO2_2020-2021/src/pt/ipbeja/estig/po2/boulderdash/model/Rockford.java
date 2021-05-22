package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Rockford extends AbstractPosition{

    private static Rockford instance = null;
    private int rockfordLives;

    private Rockford(int line, int col) {
        super(line, col);
        this.rockfordLives = 5;
        System.out.println("spawned Rockford...");
    }

    public static Rockford getInstance(int line, int col) {
        if(instance == null) {
            instance = new Rockford(line,  col);
        }
        return instance;
    }

    public int getRockfordLives() {
        return this.rockfordLives;
    }

    public void setRockfordLives(int rockfordLives) {
        this.rockfordLives = rockfordLives;
    }

    @Override
    public boolean possibleMoveTo() {
        return true;
    }

    @Override
    public boolean possibleEnemyMoveTo() {
        return true;
    }

    @Override
    public AbstractPosition moveTrigger() {
        return null;
    }

    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    public void rockfordMoveUp(Board board, int nLine, View view) { //line-1
        if(this.getLine() - 1 >= 0 &&
                board.getBoard()[this.getLine() - 1][this.getCol()].possibleMoveTo()
                && this.getLine() > 0 && this.getLine() < nLine){
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger events
            board.triggerUp();
            //swaps target with rockford
            board.getBoard()[this.getLine() - 1][this.getCol()] = this;
            this.setLine(this.getLine() - 1);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[this.getLine() + 1][this.getCol()]);
            board.checkWin();
        }
    }

    public void rockfordMoveDown(Board board, int nLine, View view) { //line+1
        if(this.getLine() + 1 < nLine &&
                board.getBoard()[this.getLine() + 1][this.getCol()].possibleMoveTo()
                && this.getLine() >= 0 && this.getLine() < nLine){
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger events
            board.triggerDown();
            //swaps target with rockford
            board.getBoard()[this.getLine() + 1][this.getCol()] = this;
            this.setLine(this.getLine() + 1);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[this.getLine() - 1][this.getCol()]);
            board.checkWin();
        }
    }

    public void rockfordMoveRight(Board board, int nCol, View view){ //col+1
        if(this.getCol() + 1 < nCol &&
                board.getBoard()[this.getLine()][this.getCol() + 1].possibleMoveTo()
                && this.getCol() >= 0 && this.getCol() < nCol){
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger events
            board.triggerRight();
            //swaps target with rockford
            board.getBoard()[this.getLine()][this.getCol() + 1] = this;
            this.setCol(this.getCol() + 1);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[this.getLine()][this.getCol() - 1]);
            board.checkWin();
        }
    }

    public void rockfordMoveLeft(Board board, int nCol, View view) { //col-1
        if(this.getCol() - 1 >= 0 &&
                board.getBoard()[this.getLine()][this.getCol() - 1].possibleMoveTo()
                && this.getCol() > 0 && this.getCol() < nCol){
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger events
            board.triggerLeft();
            //swaps target with rockford
            board.getBoard()[this.getLine()][this.getCol() - 1] = this;
            this.setCol(this.getCol() - 1);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[this.getLine()][this.getCol() + 1]);
            board.checkWin();
        }
    }

    @Override
    public void setImage(GameButton button) {
        button.setRockford();
    }

    @Override
    public void print() {
        System.out.print("X");
    }

    public void rockfordGoTo(int line, int col, Board board) {
        if (board.getBoard()[line][col].possibleMoveTo()) {
            //swap rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //triggers
            int points = board.getBoard()[line][col].increaseScore();
            if (points > 0) board.setnDiamonds(board.getnDiamonds() - 1);//nDiamonds--;
            if (board.getnDiamonds() == 0) {
                board.getBoard()[board.getGate().getLine()][board.getGate().getCol()] = new Gate(board.getGate().getLine(), board.getGate().getCol());
                board.setnGates(1);
            }
            board.setScore(board.getScore() + points);
            for (Diamond diamond : board.getDiamondList()) {
                if (diamond.getLine() + 1 < board.getnLine() && board.getBoard()[diamond.getLine() + 1][diamond.getCol()].canReceiveFallingObject()) {
                    board.getBoard()[diamond.getLine()][diamond.getCol()] = new FreeTunnel(diamond.getLine(), diamond.getCol());
                    diamond.setLine(diamond.getLine() + 1);
                    board.getBoard()[diamond.getLine()][diamond.getCol()] = diamond;
                }
            }
            //swaps target with rockford
            board.getBoard()[line][col] = this;
            this.setLine(line);
            this.setCol(col);
            //checks win
            if (board.getnGates() == 1 && board.getGate().getLine() == this.getLine() && board.getGate().getCol() == this.getCol()) {
                board.setEndLvl(1);
            }
        }
    }

}

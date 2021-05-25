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

    public void rockfordMove(Board board, int nLine, int nCol, View view, int lineMovement, int colMovement) {
        int destLine = this.getLine() + lineMovement;
        int destCol = this.getCol() + colMovement;
        if(destLine >= 0 &&  destLine < nLine && destCol >= 0 && destCol < nCol &&  //verifies if the movement is within the array bounds
                board.getBoard()[destLine][destCol].possibleMoveTo() &&             // verifies if it is possible to move
                this.getLine() >= 0 && this.getLine() < nLine && this.getCol() >= 0 && this.getCol() < nCol) { //verifies if rockford is within the array
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger events
            board.triggerEvents(destLine, destCol);
            //swaps target with rockford
            board.getBoard()[destLine][destCol] = this;
            this.setLine(destLine);
            this.setCol(destCol);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[destLine - lineMovement][destCol - colMovement]);
            board.checkWin();
            //System.out.println("TURN");
            //board.printBoard();
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
}

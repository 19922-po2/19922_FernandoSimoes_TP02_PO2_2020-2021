package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Rock extends AbstractEntity {

    public Rock(int line, int col) {
        super(line, col);
        System.out.println("spawned Rock...");
    }

    /**
     * Checks if rockford can move into it.
     *
     * @return
     */
    @Override
    public boolean possibleMoveTo() {
        return false;
    }

    /**
     * Checks if enemies can move into it.
     *
     * @return
     */
    @Override
    public boolean possibleEnemyMoveTo() {
        return false;
    }

    /**
     * Checks for special triggers.
     *
     * @return
     */
    @Override
    public AbstractPosition moveTrigger() {
        return null;
    }

    /**
     * Checks if falling objects can move into it.
     *
     * @return
     */
    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    /**
     * Moves the rock
     * @param board game board
     * @param nLine number of lines in the board
     * @param nCol number of columns in the board
     * @param view games view
     */
    public void moveEntity(AbstractPosition[][] board, int nLine, int nCol, View view) {
        if (this.getLine() + 1 < nLine && board[this.getLine() + 1][this.getCol()].canReceiveFallingObject()) {
            board[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            this.setLine(this.getLine() + 1);
            board[this.getLine()][this.getCol()] = this;
            view.rockMoved(this, board[this.getLine() - 1][this.getCol()]);
        }
    }

    /**
     * Changes button image.
     *
     * @param button
     */
    @Override
    public void setImage(GameButton button) {
        button.setRock();
    }

    @Override
    public void print() {
        System.out.print("R");
    }
}

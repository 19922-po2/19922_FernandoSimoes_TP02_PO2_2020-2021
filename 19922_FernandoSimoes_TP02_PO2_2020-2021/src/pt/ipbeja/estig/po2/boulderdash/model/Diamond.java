package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Diamond extends AbstractEntity {
    private final int DIAMOND_SCORE = 100;

    public Diamond(int line, int col) {
        super(line, col);
        System.out.println("spawned Diamond...");
    }

    /**
     * Checks if rockford can move into it.
     *
     * @return
     */
    @Override
    public boolean possibleMoveTo() {
        return true;
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
     * Moves the diamond.
     *
     * @param board game board
     * @param nLine number of lines in the board
     * @param nCol  number of columns in the board
     * @param view  games view
     */
    public void moveEntity(AbstractPosition[][] board, int nLine, int nCol, View view) {
        if (this.getLine() + 1 < nLine && board[this.getLine() + 1][this.getCol()].canReceiveFallingObject()) {
            board[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            this.setLine(this.getLine() + 1);
            board[this.getLine()][this.getCol()] = this;
            view.diamondMoved(this, board[this.getLine() - 1][this.getCol()]);
        }
    }

    /**
     * Checks for triggers.
     *
     * @return
     */
    public AbstractPosition moveTrigger() {
        return new FreeTunnel(this.getLine(), this.getCol());
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
     * @return amount of score to increase.
     */
    public int increaseScore() {
        return this.DIAMOND_SCORE;
    }

    @Override
    public void print() {
        System.out.print("+");
    }

    /**
     * Changes button image.
     *
     * @param button
     */
    @Override
    public void setImage(GameButton button) {
        button.setDiamond();
    }
}

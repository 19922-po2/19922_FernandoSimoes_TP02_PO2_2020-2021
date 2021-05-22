package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Diamond extends AbstractPosition {
    private final int DIAMOND_SCORE = 100;

    public Diamond(int line, int col) {
        super(line, col);
        System.out.println("spawned Diamond...");
    }

    public boolean possibleMoveTo() {
        return true;
    }

    @Override
    public boolean possibleEnemyMoveTo() {
        return false;
    }

    public void triggerDiamondFall(AbstractPosition[][] board, int nLine, View view) {
        if (this.getLine() + 1 < nLine && board[this.getLine() + 1][this.getCol()].canReceiveFallingObject()) {
            board[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            this.setLine(this.getLine() + 1);
            board[this.getLine()][this.getCol()] = this;
            view.diamondMoved(this, board[this.getLine() - 1][this.getCol()]);
        }
    }

    @Override
    public void setImage(GameButton button) {
        button.setDiamond();
    }

    public AbstractPosition moveTrigger() {
        return new FreeTunnel(this.getLine(), this.getCol());
    }

    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    public int increaseScore() {
        return this.DIAMOND_SCORE;
    }

    @Override
    public void print() {
        System.out.print("+");
    }
}

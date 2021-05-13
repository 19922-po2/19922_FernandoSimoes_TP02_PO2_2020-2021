package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Rock extends AbstractPosition {

    public Rock(int line, int col) {
        super(line, col);
        System.out.println("spawned Rock...");
    }

    public boolean possibleMoveTo() {
        return false;
    }

    @Override
    public AbstractPosition moveTrigger() {
        return null;
    }

    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    public void triggerRockFall(AbstractPosition[][] board, int nLine, View view) {
        if (this.getLine() + 1 < nLine && board[this.getLine() + 1][this.getCol()].canReceiveFallingObject()) {
            board[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            this.setLine(this.getLine() + 1);
            board[this.getLine()][this.getCol()] = this;
            view.rockMoved(this, board[this.getLine() - 1][this.getCol()]);
        }
    }

    @Override
    public void setImage(GameButton button) {
        button.setRock();
    }

    @Override
    public void print() {
        System.out.print("R");
    }
}

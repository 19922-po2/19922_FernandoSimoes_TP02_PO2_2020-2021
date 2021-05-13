package pt.ipbeja.estig.po2.boulderdash.model;


import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public abstract class AbstractPosition {
    private int line;
    private int col;

    public AbstractPosition(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public abstract boolean possibleMoveTo();

    public abstract AbstractPosition moveTrigger();

    public abstract boolean canReceiveFallingObject();

    public int increaseScore() {
        return 0;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public abstract void print();

    public abstract void setImage(GameButton button);
}

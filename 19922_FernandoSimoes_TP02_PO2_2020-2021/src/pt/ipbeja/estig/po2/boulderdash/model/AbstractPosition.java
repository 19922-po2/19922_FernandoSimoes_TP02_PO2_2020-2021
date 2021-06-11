package pt.ipbeja.estig.po2.boulderdash.model;


import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

public abstract class AbstractPosition {
    private int line;
    private int col;

    public AbstractPosition(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public abstract boolean possibleMoveTo();

    public abstract boolean possibleEnemyMoveTo();

    public abstract boolean canReceiveFallingObject();

    /**
     * Returns the score according to the entity.
     * @return amount of score points.
     */
    public int increaseScore() {
        return 0;
    }

    public abstract void print();

    public abstract void setImage(GameButton button);

    public abstract AbstractPosition moveTrigger();
}

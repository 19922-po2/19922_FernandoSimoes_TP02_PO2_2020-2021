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

    /**
     * Checks if rockford can move into it.
     *
     * @return
     */
    public abstract boolean possibleMoveTo();

    /**
     * Checks if enemies can move into it.
     *
     * @return
     */
    public abstract boolean possibleEnemyMoveTo();

    /**
     * Checks if falling objects can move into it.
     *
     * @return
     */
    public abstract boolean canReceiveFallingObject();

    /**
     * Returns the score according to the entity.
     *
     * @return amount of score points.
     */
    public int increaseScore() {
        return 0;
    }

    /**
     * Changes button image.
     *
     * @param button
     */
    public abstract void setImage(GameButton button);

    /**
     * Checks for special triggers.
     *
     * @return
     */
    public abstract AbstractPosition moveTrigger();

    public abstract void print();
}

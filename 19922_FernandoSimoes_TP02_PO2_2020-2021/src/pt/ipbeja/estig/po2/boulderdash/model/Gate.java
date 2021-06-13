package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Gate extends AbstractEntity {
    public Gate(int line, int col) {
        super(line, col);
    }

    /**
     * Checks if rockford can move into it.
     *
     * @return
     */
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
     * Checks for special triggers.
     *
     * @return
     */
    public AbstractPosition moveTrigger() {
        return this;
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
     * Changes button image.
     *
     * @param button
     */
    @Override
    public void setImage(GameButton button) {
        button.setGate();
    }

    @Override
    public void print() {
        System.out.print("G");
    }

    @Override
    public void moveEntity(AbstractPosition[][] board, int nLine, int nCol, View view) {
        //Once a gate spawns it can't move during the level
    }
}

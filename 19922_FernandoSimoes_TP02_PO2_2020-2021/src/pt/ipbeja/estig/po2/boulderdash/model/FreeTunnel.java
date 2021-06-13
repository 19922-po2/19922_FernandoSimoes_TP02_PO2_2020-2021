package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class FreeTunnel extends AbstractPosition {
    public FreeTunnel(int line, int col) {
        super(line, col);
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
        return true;
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
        return true;
    }

    /**
     * Changes button image.
     *
     * @param button
     */
    @Override
    public void setImage(GameButton button) {
        button.setFreeTunnel();
    }

    @Override
    public void print() {
        System.out.print("L");
    }
}

package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class OccupiedTunnel extends AbstractPosition {

    public OccupiedTunnel(int line, int col) {
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
        return false;
    }

    /**
     * Checks for special triggers.
     *
     * @return returns a free tunnel to substitute the occupied tunnel.
     */
    @Override
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
     * Changes button image.
     *
     * @param button
     */
    @Override
    public void setImage(GameButton button) {
        button.setOccupiedTunnel();
    }

    @Override
    public void print() {
        System.out.print("O");
    }
}

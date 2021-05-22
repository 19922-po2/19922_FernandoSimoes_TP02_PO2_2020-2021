package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Gate extends AbstractPosition {
    public Gate(int line, int col) {
        super(line, col);
    }

    public boolean possibleMoveTo() {
        return true;
    }

    @Override
    public boolean possibleEnemyMoveTo() {
        return false;
    }

    public AbstractPosition moveTrigger() {
        return this;
    }

    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    @Override
    public void setImage(GameButton button) {
        button.setGate();
    }

    @Override
    public void print() {
        System.out.print("G");
    }
}

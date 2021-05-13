package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class FreeTunnel extends AbstractPosition {
    public FreeTunnel(int line, int col) {
        super(line, col);
    }

    public boolean possibleMoveTo() {
        return true;
    }

    public AbstractPosition moveTrigger() {
        return null;
    }

    @Override
    public boolean canReceiveFallingObject() {
        return true;
    }

    @Override
    public void setImage(GameButton button) {
        button.setFreeTunnel();
    }

    @Override
    public void print() {
        System.out.print("L");
    }
}

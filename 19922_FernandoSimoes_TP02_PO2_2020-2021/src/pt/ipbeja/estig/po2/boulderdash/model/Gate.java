package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Gate extends AbstractEntity {
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

    @Override
    public void moveEntity(AbstractPosition[][] board, int nLine, int nCol, View view) {
        //Once a gate spawns it can't move during the level
    }
}

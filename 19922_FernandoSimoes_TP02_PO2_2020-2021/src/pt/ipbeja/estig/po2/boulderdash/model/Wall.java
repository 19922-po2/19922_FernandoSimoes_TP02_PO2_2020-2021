package pt.ipbeja.estig.po2.boulderdash.model;

import javafx.scene.control.Button;
import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 */

public class Wall extends AbstractPosition {
    public Wall(int line, int col) {
        super(line, col);
    }

    public boolean possibleMoveTo() {
        return false;
    }

    @Override
    public boolean possibleEnemyMoveTo() {
        return false;
    }

    public AbstractPosition moveTrigger() {
        return null;
    }

    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    @Override
    public void print() {
        System.out.print("W");
    }

    @Override
    public void setImage(GameButton button) {
        button.setWall();
    }
}

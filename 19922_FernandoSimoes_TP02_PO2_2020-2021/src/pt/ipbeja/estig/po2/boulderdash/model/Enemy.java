package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

public class Enemy extends AbstractPosition {

    public Enemy(int line, int col) {
        super(line, col);
    }

    public void enemyMove(Board board, int nLine, View view) {
        //TODO
    }

    @Override
    public boolean possibleMoveTo() {
        return false;
    }

    @Override
    public AbstractPosition moveTrigger() {
        return null;
    }

    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    @Override
    public void print() {
        System.out.print("!");
    }

    @Override
    public void setImage(GameButton button) {
        button.setEnemy();
    }
}

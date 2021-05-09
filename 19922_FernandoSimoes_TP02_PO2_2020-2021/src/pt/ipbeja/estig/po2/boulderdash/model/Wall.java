package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Fernando Simões nº 19922
 */

public class Wall extends AbstractPosition{
    public Wall(int line, int col) {
        super(line, col);
    }

    public boolean possibleMoveTo() {
        return false;
    }

    public AbstractPosition moveTrigger() {
        return null;
    }

    @Override
    public boolean canReceiveFallingObject(){
        return false;
    }

    @Override
    public char print() {
        //System.out.print("W");
        return 'W';
    }
}

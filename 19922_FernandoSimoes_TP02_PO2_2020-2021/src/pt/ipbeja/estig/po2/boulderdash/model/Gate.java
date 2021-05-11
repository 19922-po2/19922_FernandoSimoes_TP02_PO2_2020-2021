package pt.ipbeja.estig.po2.boulderdash.model;

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

    public AbstractPosition moveTrigger() {
        return this;
    }

    @Override
    public char print() {
        //System.out.print("G");
        return 'G';
    }
}

package pt.ipbeja.estig.po2.boulderdash.model;

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
    public char print() {
        System.out.print("L");
        return 'L';
    }
}

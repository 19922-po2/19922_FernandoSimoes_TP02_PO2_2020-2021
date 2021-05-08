package pt.ipbeja.estig.po2.boulderdash.model;

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
    public void print() {
        System.out.print("G");
    }
}

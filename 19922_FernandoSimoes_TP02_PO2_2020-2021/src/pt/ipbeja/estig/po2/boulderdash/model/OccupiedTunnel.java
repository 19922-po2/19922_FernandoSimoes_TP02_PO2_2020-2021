package pt.ipbeja.estig.po2.boulderdash.model;

public class OccupiedTunnel extends AbstractPosition{

    public OccupiedTunnel(int line, int col) {
        super(line, col);
    }


    public boolean possibleMoveTo() {
        return true;
    }

    public AbstractPosition moveTrigger() {
        return new FreeTunnel(this.getLine(), this.getCol());
    }

    @Override
    public char print() {
        System.out.print("O");
        return 'O';
    }
}

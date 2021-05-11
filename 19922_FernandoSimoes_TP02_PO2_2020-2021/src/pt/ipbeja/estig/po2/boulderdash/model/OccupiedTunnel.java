package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Fernando Simões nº 19922
 */

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
    public boolean canReceiveFallingObject(){
        return false;
    }

    @Override
    public char print() {
        //System.out.print("O");
        return 'O';
    }
}

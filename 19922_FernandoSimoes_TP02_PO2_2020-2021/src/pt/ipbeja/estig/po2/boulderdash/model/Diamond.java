package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Fernando Simões nº 19922
 */

public class Diamond extends AbstractPosition {
    private final int DIAMOND_SCORE = 100;
    public Diamond(int line, int col) {
        super(line, col);
    }

    public boolean possibleMoveTo() {
        return true;
    }

    public AbstractPosition moveTrigger() {
        return new FreeTunnel(this.getLine(), this.getCol());
    }

    public int increaseScore(){
        return this.DIAMOND_SCORE;
    }

    @Override
    public char print() {
        //System.out.print("+");
        return '+';
    }
}

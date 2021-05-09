package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Fernando Simões nº 19922
 */

public class Rock extends AbstractPosition {

    public Rock(int line, int col) {
        super(line, col);
    }

    public boolean possibleMoveTo() {
        return false;
    }

    public void rockFall(){

    }

    @Override
    public char print() {
        //System.out.print("R");
        return 'R';
    }
}

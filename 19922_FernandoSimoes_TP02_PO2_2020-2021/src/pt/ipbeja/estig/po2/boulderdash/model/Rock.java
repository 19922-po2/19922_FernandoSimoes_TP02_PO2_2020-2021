package pt.ipbeja.estig.po2.boulderdash.model;

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
    public void print() {
        System.out.print("R");
    }
}

package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Fernando Simões nº 19922
 */

public class Rockford extends AbstractPosition{

    public Rockford(int line, int col) {
        super(line, col);
    }


    @Override
    public char print() {
        //System.out.print("X");
        return 'X';
    }
}

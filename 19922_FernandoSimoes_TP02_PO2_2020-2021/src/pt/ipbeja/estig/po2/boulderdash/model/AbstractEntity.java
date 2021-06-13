package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Fernando Simões nº 19922
 */

public abstract class AbstractEntity extends AbstractPosition {

    public AbstractEntity(int line, int col) {
        super(line, col);
    }

    /**
     * Abstract method to move each entity in the board
     *
     * @param board game board
     * @param nLine number of lines in the board
     * @param nCol  number of columns in the board
     * @param view  games view
     */
    public abstract void moveEntity(AbstractPosition[][] board, int nLine, int nCol, View view);
}

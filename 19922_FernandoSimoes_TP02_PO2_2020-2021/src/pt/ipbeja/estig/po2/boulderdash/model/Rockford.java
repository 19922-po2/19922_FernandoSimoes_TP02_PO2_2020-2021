package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

/**
 * @author Fernando Simões nº 19922
 * Rockford is a singleton class playable by the user.
 */
public class Rockford extends AbstractEntity {

    private static Rockford instance = null;
    private int rockfordLives;

    private Rockford(int line, int col) {
        super(line, col);
        this.rockfordLives = 5;
        System.out.println("spawned Rockford...");
    }

    public static Rockford getInstance(int line, int col) {
        if (instance == null) {
            instance = new Rockford(line, col);
        }
        return instance;
    }

    /**
     * Gets rockford lives.
     *
     * @return rockford lives.
     */
    public int getRockfordLives() {
        return this.rockfordLives;
    }

    /**
     * Sets rockford lives.
     *
     * @param rockfordLives number of rockford lives.
     */
    public void setRockfordLives(int rockfordLives) {
        this.rockfordLives = rockfordLives;
    }

    /**
     * Checks if rockford can move into it.
     *
     * @return
     */
    @Override
    public boolean possibleMoveTo() {
        return false;
    }

    /**
     * Checks if enemies can move into it.
     *
     * @return
     */
    @Override
    public boolean possibleEnemyMoveTo() {
        return true;
    }

    /**
     * Checks for special triggers.
     *
     * @return
     */
    @Override
    public AbstractPosition moveTrigger() {
        return null;
    }

    /**
     * Checks if falling objects can move into it.
     *
     * @return
     */
    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    /**
     * Moves Rockford according to user input.
     *
     * @param board        Game board.
     * @param nLine        Number of lines in the board.
     * @param nCol         Number of columns in the board.
     * @param view         Game view.
     * @param lineMovement Destination line (left or right).
     * @param colMovement  Destination Column (up or down).
     */
    public void moveEntity(Board board, int nLine, int nCol, View view, int lineMovement, int colMovement) {
        int destLine = this.getLine() + lineMovement;
        int destCol = this.getCol() + colMovement;
        if (destLine >= 0 && destLine < nLine && destCol >= 0 && destCol < nCol &&  //verifies if the movement is within the array bounds
                board.getBoard()[destLine][destCol].possibleMoveTo() &&             // verifies if it is possible to move
                this.getLine() >= 0 && this.getLine() < nLine && this.getCol() >= 0 && this.getCol() < nCol) { //verifies if rockford is within the array
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger events
            board.triggerEvents(destLine, destCol);
            //swaps target with rockford
            board.getBoard()[destLine][destCol] = this;
            this.setLine(destLine);
            this.setCol(destCol);
            board.checkEnemyCollision();
            //refresh view
            view.rockfordMoved(this, board.getBoard()[destLine - lineMovement][destCol - colMovement]);
            board.checkWin();
        }
    }

    /**
     * Changes button image.
     *
     * @param button
     */
    @Override
    public void setImage(GameButton button) {
        button.setRockford();
    }

    @Override
    public void print() {
        System.out.print("X");
    }

    @Override
    public void moveEntity(AbstractPosition[][] board, int nLine, int nCol, View view) {
        //unused
    }
}

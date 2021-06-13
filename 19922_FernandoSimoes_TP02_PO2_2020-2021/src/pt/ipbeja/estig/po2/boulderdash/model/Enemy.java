package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

import java.util.Random;

/**
 * @author Fernando Simões nº 19922
 */

public class Enemy extends AbstractEntity {

    Random randInt = new Random(); //rng to decide enemy movement

    public Enemy(int line, int col) {
        super(line, col);
        System.out.println("spawned Enemy...");
    }

    /**
     * Generates a random number to move the enemy.
     *
     * @param board game board
     * @param nLine number of lines in the board
     * @param nCol  number of columns in the board
     * @param view  games view
     */
    public void moveEntity(AbstractPosition[][] board, int nLine, int nCol, View view) {
        int randomPosition;
        boolean moved = false;
        // 0-up, 1-right, 2-down, 3-left, checks for all possible moves
        while (!moved) {
            randomPosition = randInt.nextInt(4);//number between 0~3
            switch (randomPosition) {
                case 0:
                    moved = enemyMove(board, nLine, nCol, view, -1, 0);
                    break;
                case 1:
                    moved = enemyMove(board, nLine, nCol, view, 0, 1);
                    break;
                case 2:
                    moved = enemyMove(board, nLine, nCol, view, 1, 0);
                    break;
                case 3:
                    moved = enemyMove(board, nLine, nCol, view, 0, -1);
                    break;
            }
        }
    }

    /**
     * @param board        game board
     * @param nLine        number of lines in the board
     * @param nCol         number of columns in the board
     * @param view         games view
     * @param lineMovement Destination line (left or right).
     * @param colMovement  Destination Column (up or down).
     * @return returns true if the movement was successful.
     */
    public boolean enemyMove(AbstractPosition[][] board, int nLine, int nCol, View view, int lineMovement, int colMovement) {
        int destLine = this.getLine() + lineMovement;
        int destCol = this.getCol() + colMovement;
        if (destLine >= 0 && destLine < nLine && destCol >= 0 && destCol < nCol &&  //verifies if the movement is within the array bounds
                board[destLine][destCol].possibleEnemyMoveTo() &&             // verifies if it is possible to move
                this.getLine() >= 0 && this.getLine() < nLine && this.getCol() >= 0 && this.getCol() < nCol) { //verifies if enemy is within the array
            //swaps enemy with free tunnel
            board[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //swaps target with enemy
            board[destLine][destCol] = this;
            this.setLine(destLine);
            this.setCol(destCol);
            //refresh view
            view.enemyMoved(this, board[destLine - lineMovement][destCol - colMovement]);
            return true;
        }
        return false;
    }

    /**
     * Checks if rockford can move into it.
     *
     * @return
     */
    @Override
    public boolean possibleMoveTo() {
        return true;
    }

    /**
     * Checks if enemies can move into it.
     *
     * @return
     */
    @Override
    public boolean possibleEnemyMoveTo() {
        return false;
    }

    /**
     * Checks for triggers.
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

    @Override
    public void print() {
        System.out.print("!");
    }

    /**
     * Changes button image.
     *
     * @param button
     */
    @Override
    public void setImage(GameButton button) {
        button.setEnemy();
    }
}

package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.GameButton;

import java.util.Random;

public class Enemy extends AbstractPosition {

    Random randInt = new Random(); //rng to decide enemy movement

    public Enemy(int line, int col) {
        super(line, col);
        System.out.println("spawned Enemy...");
    }

    // 0-up, 1-right, 2-down, 3-left, checks for all possible moves
    public void enemyMove(Board board, int nLine, int nCol, View view) {
        int randomPosition;
        boolean moved = false;

        while (!moved) {
            randomPosition = randInt.nextInt(4);//number between 0~3
            switch (randomPosition) {
                case 0:
                    moved = enemyMoveUp(board, nLine, view);
                    break;
                case 1:
                    moved = enemyMoveRight(board, nCol, view);
                    break;
                case 2:
                    moved = enemyMoveDown(board, nLine, view);
                    break;
                case 3:
                    moved = enemyMoveLeft(board, nCol, view);
                    break;
            }
        }
    }

    public boolean enemyMoveUp(Board board, int nLine, View view) { //line-1
        if (this.getLine() - 1 >= 0 &&
                board.getBoard()[this.getLine() - 1][this.getCol()].possibleEnemyMoveTo()
                && this.getLine() > 0 && this.getLine() < nLine) {
            //swaps enemy with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //swaps target with enemy
            board.getBoard()[this.getLine() - 1][this.getCol()] = this;
            this.setLine(this.getLine() - 1);
            //refresh view
            view.enemyMoved(this, board.getBoard()[this.getLine() + 1][this.getCol()]);
            return true;
        }
        return false;
    }

    public boolean enemyMoveDown(Board board, int nLine, View view) { //line+1
        if (this.getLine() + 1 < nLine &&
                board.getBoard()[this.getLine() + 1][this.getCol()].possibleEnemyMoveTo()
                && this.getLine() >= 0 && this.getLine() < nLine) {
            //swaps enemy with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //swaps target with enemy
            board.getBoard()[this.getLine() + 1][this.getCol()] = this;
            this.setLine(this.getLine() + 1);
            //refresh view
            view.enemyMoved(this, board.getBoard()[this.getLine() - 1][this.getCol()]);
            return true;
        }
        return false;
    }

    public boolean enemyMoveRight(Board board, int nCol, View view) { //col+1
        if (this.getCol() + 1 < nCol &&
                board.getBoard()[this.getLine()][this.getCol() + 1].possibleEnemyMoveTo()
                && this.getCol() >= 0 && this.getCol() < nCol) {
            //swaps enemy with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //swaps target with enemy
            board.getBoard()[this.getLine()][this.getCol() + 1] = this;
            this.setCol(this.getCol() + 1);
            //refresh view
            view.enemyMoved(this, board.getBoard()[this.getLine()][this.getCol() - 1]);
            return true;
        }
        return false;
    }

    public boolean enemyMoveLeft(Board board, int nCol, View view) { //col-1
        if (this.getCol() - 1 >= 0 &&
                board.getBoard()[this.getLine()][this.getCol() - 1].possibleEnemyMoveTo()
                && this.getCol() > 0 && this.getLine() < nCol) {
            //swaps enemy with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //swaps target with enemy
            board.getBoard()[this.getLine()][this.getCol() - 1] = this;
            this.setCol(this.getCol() - 1);
            //refresh view
            view.enemyMoved(this, board.getBoard()[this.getLine()][this.getCol() + 1]);
            return true;
        }
        return false;
    }

    @Override
    public boolean possibleMoveTo() {
        return false;
    }

    @Override
    public boolean possibleEnemyMoveTo() {
        return false;
    }

    @Override
    public AbstractPosition moveTrigger() {
        return null;
    }

    @Override
    public boolean canReceiveFallingObject() {
        return false;
    }

    @Override
    public void print() {
        System.out.print("!");
    }

    @Override
    public void setImage(GameButton button) {
        button.setEnemy();
    }
}

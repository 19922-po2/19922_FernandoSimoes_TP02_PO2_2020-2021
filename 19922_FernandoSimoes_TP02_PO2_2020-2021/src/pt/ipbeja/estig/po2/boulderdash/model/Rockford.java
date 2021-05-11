package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Fernando Simões nº 19922
 */

public class Rockford extends AbstractPosition{

    public Rockford(int line, int col) {
        super(line, col);
        System.out.println("spawned Rockford...");
    }

    public void rockfordMoveUp(Board board, int nLine, View view) { //line-1
        if(this.getLine() - 1 >= 0 &&
                board.getBoard()[this.getLine() - 1][this.getCol()].possibleMoveTo()
                && this.getLine() > 0 && this.getLine() < nLine){
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger
            board.triggerUp();
            //swaps target with rockford
            board.getBoard()[this.getLine() - 1][this.getCol()] = this;
            this.setLine(this.getLine() - 1);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[this.getLine() + 1][this.getCol()]);
            board.checkWin();
        }
    }

    public void rockfordMoveDown(Board board, int nLine, View view) { //line+1
        if(this.getLine() + 1 < nLine &&
                board.getBoard()[this.getLine() + 1][this.getCol()].possibleMoveTo()
                && this.getLine() >= 0 && this.getLine() < nLine){
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger
            board.triggerDown();
            //swaps target with rockford
            board.getBoard()[this.getLine() + 1][this.getCol()] = this;
            this.setLine(this.getLine() + 1);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[this.getLine() - 1][this.getCol()]);
            board.checkWin();
        }
    }

    public void rockfordMoveRight(Board board, int nCol, View view){ //col+1
        if(this.getCol() + 1 < nCol &&
                board.getBoard()[this.getLine()][this.getCol() + 1].possibleMoveTo()
                && this.getCol() >= 0 && this.getCol() < nCol){
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger
            board.triggerRight();
            //swaps target with rockford
            board.getBoard()[this.getLine()][this.getCol() + 1] = this;
            this.setCol(this.getCol() + 1);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[this.getLine()][this.getCol() - 1]);
            board.checkWin();
        }
    }

    public void rockfordMoveLeft(Board board, int nCol, View view) { //col-1
        if(this.getCol() - 1 >= 0 &&
                board.getBoard()[this.getLine()][this.getCol() - 1].possibleMoveTo()
                && this.getCol() > 0 && this.getCol() < nCol){
            //swaps rockford with free tunnel
            board.getBoard()[this.getLine()][this.getCol()] = new FreeTunnel(this.getLine(), this.getCol());
            //trigger
            board.triggerLeft();
            //swaps target with rockford
            board.getBoard()[this.getLine()][this.getCol() - 1] = this;
            this.setCol(this.getCol() - 1);
            //refresh view
            view.rockfordMoved(this, board.getBoard()[this.getLine()][this.getCol() + 1]);
            board.checkWin();
        }
    }

    @Override
    public char print() {
        //System.out.print("X");
        return 'X';
    }
}

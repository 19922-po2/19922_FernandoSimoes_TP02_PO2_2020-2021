package pt.ipbeja.estig.po2.boulderdash.model;

public abstract class AbstractPosition {
    private int line;
    private int col;

    public AbstractPosition(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean possibleMoveTo() {
        return false;
    }

    public AbstractPosition moveTrigger() {
        return null;
    }

    public int increaseScore(){
        return 0;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public abstract char print();
}

package pt.ipbeja.estig.po2.boulderdash.model;

public class Wall extends AbstractPosition{
    public Wall(int line, int col) {
        super(line, col);
    }

    public boolean possibleMoveTo() {
        return false;
    }

    public AbstractPosition moveTrigger() {
        return null;
    }

    @Override
    public char print() {
        System.out.print("W");
        return 'W';
    }
}

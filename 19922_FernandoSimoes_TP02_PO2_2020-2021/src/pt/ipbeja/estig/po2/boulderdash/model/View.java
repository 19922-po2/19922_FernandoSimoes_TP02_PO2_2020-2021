package pt.ipbeja.estig.po2.boulderdash.model;

public interface View {
    void rockfordMoved(AbstractPosition rockford, AbstractPosition entity);
    void gateAppeared(AbstractPosition gate);
    void gameWon(int score);

}

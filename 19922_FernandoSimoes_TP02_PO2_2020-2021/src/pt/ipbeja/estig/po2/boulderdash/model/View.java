package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Fernando Simões nº 19922
 */

public interface View {
    void rockfordMoved(AbstractPosition rockford, AbstractPosition entity);

    void gateAppeared(AbstractPosition gate);

    void lvlWon(int score);

    void diamondMoved(AbstractPosition diamond, AbstractPosition entity);

    void rockMoved(AbstractPosition rock, AbstractPosition entity);

    void resetBoard(Board board);

    void setDiamondCount();

    void setGameScore();

    void setRockfordLivesCount();

    void timerRefresh(int timeValue);

    void enemyMoved(AbstractPosition enemy, AbstractPosition entity);

    void showError(String message);

    void gameOver(int score);
}

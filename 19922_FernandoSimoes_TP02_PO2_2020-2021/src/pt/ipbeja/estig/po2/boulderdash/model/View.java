package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.List;

/**
 * @author Fernando Simões nº 19922
 */

public interface View {

    /**
     * Notifies the view that rockford moved.
     *
     * @param rockford rockford object.
     * @param entity   entity that rockford moved into.
     */
    void rockfordMoved(AbstractPosition rockford, AbstractPosition entity);

    /**
     * Notifies the view that a gate appeared.
     *
     * @param gate gate object.
     */
    void gateAppeared(AbstractPosition gate);

    /**
     * Notifies the view that the level is over.
     *
     * @param score current score.
     */
    void lvlWon(int score);

    /**
     * Notifies the view that a diamond moved.
     *
     * @param diamond diamond object.
     * @param entity  entity that the diamond moved into.
     */
    void diamondMoved(AbstractPosition diamond, AbstractPosition entity);

    /**
     * Notifies the view that a rock moved.
     *
     * @param rock   rock object.
     * @param entity entity that the rock moved into.
     */
    void rockMoved(AbstractPosition rock, AbstractPosition entity);

    /**
     * Notifies the view that the board was reset.
     *
     * @param board game's board.
     */
    void resetBoard(Board board);

    /**
     * Updates the view with the current number od diamonds in the board.
     */
    void setDiamondCount();

    /**
     * Updates the view with the current score.
     */
    void setGameScore();

    /**
     * Updates the view with the current number of lives.
     */
    void setRockfordLivesCount();

    /**
     * Updates the timer in the view.
     *
     * @param timeValue current time elapsed.
     */
    void timerRefresh(int timeValue);

    /**
     * Notifies the view that an enemy moved.
     *
     * @param enemy  enemy object.
     * @param entity entity that the nemy moved into.
     */
    void enemyMoved(AbstractPosition enemy, AbstractPosition entity);

    /**
     * Show the user an error message.
     *
     * @param message String to include in the error message.
     */
    void showError(String message);

    /**
     * Shows the user the end game info.
     *
     * @param score final score.
     */
    void gameOver(int score);

    /**
     * Shows the user the top 5 scores after the game ends.
     *
     * @param highScores list containing the top 5 scores sorted.
     */
    void showScores(List<Score> highScores);

    /**
     * Notifies the view that rockford died.
     */
    void rockfordDied();
}

package pt.ipbeja.estig.po2.boulderdash.gui;

import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;
import pt.ipbeja.estig.po2.boulderdash.model.Board;
import pt.ipbeja.estig.po2.boulderdash.model.Score;
import pt.ipbeja.estig.po2.boulderdash.model.View;

import java.util.List;

/**
 * @author Fernando Simões nº 19922
 * Dummy Class for testing purpuses.
 */
public class DummyView implements View {

    @Override
    public void rockfordMoved(AbstractPosition rockford, AbstractPosition entity) {

    }

    @Override
    public void gateAppeared(AbstractPosition gate) {

    }

    @Override
    public void lvlWon(int score) {

    }

    @Override
    public void diamondMoved(AbstractPosition diamond, AbstractPosition entity) {

    }

    @Override
    public void rockMoved(AbstractPosition rock, AbstractPosition entity) {

    }

    @Override
    public void resetBoard(Board board) {

    }

    @Override
    public void setDiamondCount() {

    }

    @Override
    public void setGameScore() {

    }

    @Override
    public void setRockfordLivesCount() {

    }

    @Override
    public void timerRefresh(int timeValue) {

    }

    @Override
    public void enemyMoved(AbstractPosition enemy, AbstractPosition entity) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void gameOver(int score) {

    }

    @Override
    public void showScores(List<Score> highScores) {

    }

    @Override
    public void rockfordDied() {

    }
}

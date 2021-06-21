package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.Comparator;

/**
 * @author Fernando Simões nº 19922
 * Class to compare scores objects by the 'score' attribute
 */

public class ScoreComparator implements Comparator<Score> {

    public int compare(Score score1, Score score2) {
        return -Integer.compare(Integer.parseInt(score1.getScore()), Integer.parseInt(score2.getScore()));
    }
}

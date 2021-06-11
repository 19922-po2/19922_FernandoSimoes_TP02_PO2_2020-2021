package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {

    public int compare(Score score1, Score score2) {
        return -Integer.compare(Integer.parseInt(score1.getScore()), Integer.parseInt(score2.getScore()));
    }
}

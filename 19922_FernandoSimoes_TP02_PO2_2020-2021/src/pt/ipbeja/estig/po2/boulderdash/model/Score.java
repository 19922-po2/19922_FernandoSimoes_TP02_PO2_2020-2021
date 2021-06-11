package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.Objects;

/**
 * @author Fernando Simões nº 19922
 */

public class Score implements Comparable<Score> {

    private String name;
    private String level;
    private String score;

    public Score(String name, String level, String score) {
        this.name = name;
        this.level = level;
        this.score = score;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.level + ", " + this.score;
    }

    @Override
    public int compareTo(Score o) {
        int scoreComparison = this.score.compareTo(o.score);
        if (scoreComparison == 0) {
            return this.name.compareTo(o.name);
        } else {
            return scoreComparison;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return Objects.equals(name, score1.name) &&
                Objects.equals(level, score1.level) &&
                Objects.equals(score, score1.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, score);
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

package nick.leaderboard.board;

import java.util.Objects;

/**
 * Expected that this class is used only in single thread environment
 */
public class ScoreItem implements Comparable<ScoreItem> {
    private final Integer userId;
    private Integer points;


    public ScoreItem(Integer userId, Integer score) {
        this.userId = userId;
        this.points = score;
    }

    Integer getPoints() {
        return points;
    }

    Integer getUserId() {
        return userId;
    }

    void addPoints(Integer amount) {
        points += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreItem item = (ScoreItem) o;
        return userId.equals(item.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public int compareTo(ScoreItem other) {
        return this.getPoints().compareTo(other.getPoints());
    }
}

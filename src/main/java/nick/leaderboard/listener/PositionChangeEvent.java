package nick.leaderboard.listener;

import java.util.ArrayList;
import java.util.List;

public class PositionChangeEvent {

    private Integer receiverId;
    private Integer totalCount;
    private List<PositionItem> items = new ArrayList<>();

    public PositionChangeEvent(Integer receiverId, Integer totalCount) {
        this.receiverId = receiverId;
        this.totalCount = totalCount;
    }

    public void addItem(Integer position, Integer userId, Integer points) {
        items.add(new PositionItem(position, userId, points));
    }

    public List<PositionItem> getItems() {
        return items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public class PositionItem {
        private final Integer position;
        private final Integer userId;
        private final Integer points;

        public PositionItem(Integer position, Integer userId, Integer points) {
            this.position = position;
            this.userId = userId;
            this.points = points;
        }

        public Integer getPosition() {
            return position;
        }

        public Integer getUserId() {
            return userId;
        }

        public Integer getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return "PositionItem{" +
                    "position=" + position +
                    ", userId=" + userId +
                    ", points=" + points +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PositionChangeEvent{" +
                "receiverId=" + receiverId +
                ", totalCount=" + totalCount +
                ", items=" + items +
                '}';
    }
}

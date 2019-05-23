package nick.leaderboard.board;

import nick.leaderboard.listener.PositionChangeEvent;
import nick.leaderboard.listener.PositionChangeListener;

import java.util.ArrayList;
import java.util.List;

public class TestPositionChangeListener implements PositionChangeListener {

    private List<PositionChangeEvent> receivedEvents = new ArrayList<>();

    @Override
    public void notify(PositionChangeEvent event) {
        receivedEvents.add(event);
    }

    List<PositionChangeEvent> getReceivedEvents() {
        return receivedEvents;
    }

    List<PositionChangeEvent> findEventForUser(Integer userId) {
        List<PositionChangeEvent> eventsForUser = new ArrayList<>();
        for (PositionChangeEvent event : receivedEvents) {
            if (event.getReceiverId().equals(userId)) {
                eventsForUser.add(event);
            }
        }
        return eventsForUser;
    }

    PositionChangeEvent getLastEvent() {
        return receivedEvents.get(receivedEvents.size() - 1);
    }

    void clear() {
        receivedEvents.clear();
    }

    boolean hasEventFor(Integer userId) {
        for (PositionChangeEvent event : receivedEvents) {
            if (event.getReceiverId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
}

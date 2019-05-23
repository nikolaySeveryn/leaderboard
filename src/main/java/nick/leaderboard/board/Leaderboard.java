package nick.leaderboard.board;

import nick.leaderboard.listener.PositionChangeEvent;
import nick.leaderboard.listener.PositionChangeListener;

import java.util.*;

public class Leaderboard {

    private static final int NEIGHBORS_COUNT = 5;
    private static final int FIRST_NEIGHBOR_OFFSET = (int) Math.ceil(NEIGHBORS_COUNT / 2);
    private static final int LAST_NEIGHBOR_OFFSET = (int) Math.floor(NEIGHBORS_COUNT / 2);
    private static final Leaderboard INSTANCE = new Leaderboard();

    private final List<ScoreItem> scores = Collections.synchronizedList(new LinkedList<>());
    private final ArrayList<PositionChangeListener> listeners = new ArrayList<>();


    public static Leaderboard getInstance() {
        return INSTANCE;
    }

    public void addUsers(List<ScoreItem> items) {
        synchronized (scores) {
            Set<ScoreItem> usersToNotify = new HashSet<>();
            for (ScoreItem item : items) {
                Integer userId = item.getUserId();
                if (scoreItemExists(userId)) {
                    throw new UserAlreadyAddedToScoreboardExeption("User with id " + userId + " already in present in the scoreboard");
                }
                ScoreItem newItem = new ScoreItem(userId, item.getPoints());
                scores.add(newItem);
                scores.sort(Collections.reverseOrder());
                usersToNotify.addAll(findNeighbors(newItem));
                usersToNotify.addAll(findUsersToNotify(newItem));
            }

            notifyUsers(usersToNotify);
        }
    }

    void addUser(Integer userId, Integer points) {
        synchronized (scores) {
            if (scoreItemExists(userId)) {
                throw new UserAlreadyAddedToScoreboardExeption("User with id " + userId + " already in present in the scoreboard");
            }
            ScoreItem newItem = new ScoreItem(userId, points);
            scores.add(newItem);
            scores.sort(Collections.reverseOrder());

            notifyUsers(findUsersToNotify(newItem));
        }
    }

    public void addPoints(Integer userId, Integer amount) {
        synchronized (scores) {
            ScoreItem updatingItem = findScoreItem(userId);
            updatingItem.addPoints(amount);
            scores.sort(Collections.reverseOrder());
            notifyUsers(findUsersToNotify(updatingItem));
        }
    }

    public void subscribe(PositionChangeListener listener) {
        listeners.add(listener);
    }

    public List<Integer> getUserIds() {
        List<Integer> ids = new ArrayList<>(scores.size());
        for (ScoreItem item : scores) {
            ids.add(item.getUserId());
        }
        return ids;

    }

    void clear() {
        scores.clear();
    }

    private void notifyUsers(Set<ScoreItem> receivers) {
        for (ScoreItem receiver : receivers) {
            List<ScoreItem> neighbors = findNeighbors(receiver);
            PositionChangeEvent event = new PositionChangeEvent(receiver.getUserId(), scores.size());
            for (ScoreItem neighbor : neighbors) {
                int position = scores.indexOf(neighbor) + 1;
                event.addItem(position, neighbor.getUserId(), neighbor.getPoints());
            }
            for (PositionChangeListener listener : listeners) {
                listener.notify(event);
            }
        }
    }

    private Set<ScoreItem> findUsersToNotify(ScoreItem updatedUser) {
        Set<ScoreItem> usersToNotify = new HashSet<>();
        usersToNotify.addAll(findNeighbors(updatedUser));

        //Add top users if updated user in top
        if (scores.indexOf(updatedUser) < NEIGHBORS_COUNT) {
            ListIterator<ScoreItem> listIterator = scores.listIterator(0);
            while (listIterator.hasNext() && listIterator.nextIndex() < NEIGHBORS_COUNT) {
                usersToNotify.add(listIterator.next());
            }
        }

        //Add tail users if updated user in tail
        if (scores.indexOf(updatedUser) + 1 > scores.size() - NEIGHBORS_COUNT) {
            ListIterator<ScoreItem> listIterator = scores.listIterator(scores.size());
            while (listIterator.hasPrevious() && listIterator.previousIndex() >= scores.size() - NEIGHBORS_COUNT) {
                ScoreItem previous = listIterator.previous();
                usersToNotify.add(previous);
            }
        }

        return usersToNotify;
    }

    private List<ScoreItem> findNeighbors(ScoreItem of) {
        List<ScoreItem> neighbors = new ArrayList<>(NEIGHBORS_COUNT);

        int index = scores.indexOf(of);
        int firstIndex;
        if (index + LAST_NEIGHBOR_OFFSET > scores.size() - 1) {
            firstIndex = Math.max(0, scores.size() - NEIGHBORS_COUNT);
        } else {
            firstIndex = Math.max(0, index - FIRST_NEIGHBOR_OFFSET);
        }
        ListIterator<ScoreItem> iterator = scores.listIterator(firstIndex);
        while (iterator.hasNext() && neighbors.size() < NEIGHBORS_COUNT) {
            neighbors.add(iterator.next());
        }
        return neighbors;
    }

    private boolean scoreItemExists(Integer userId) {
        for (ScoreItem item : scores) {
            if (item.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private ScoreItem findScoreItem(Integer userId) {
        for (ScoreItem item : scores) {
            if (item.getUserId().equals(userId)) {
                return item;
            }
        }
        throw new UserNotFoundException("User with id " + userId + " not found in the leaderboard");
    }
}

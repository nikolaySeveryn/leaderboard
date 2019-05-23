package nick.leaderboard.board;


import nick.leaderboard.listener.PositionChangeEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LeaderboardTest {

    private Leaderboard leaderboard;
    private TestPositionChangeListener listener = new TestPositionChangeListener();

    @Before
    public void init() {
        leaderboard = Leaderboard.getInstance();
        leaderboard.subscribe(listener);
    }

    @After
    public void clear() {
        leaderboard.clear();
    }

    @Test
    public void addOneUserTest() {
        leaderboard.addUser(5, 600);
        List<PositionChangeEvent> events = listener.getReceivedEvents();
        assertEquals(1, events.size());
        assertEquals(1, (int) events.get(0).getTotalCount());
        assertEquals(1, (int) events.get(0).getItems().get(0).getPosition());
        assertEquals(5, (int) events.get(0).getItems().get(0).getUserId());
        assertEquals(600, (int) events.get(0).getItems().get(0).getPoints());
    }

    @Test
    public void orderTest() {
        leaderboard.addUser(5, 600);
        leaderboard.addUser(6, 300);
        leaderboard.addUser(7, 700);

        List<PositionChangeEvent.PositionItem> lastEventsItems = listener.getLastEvent().getItems();

        assertEquals(1, (int) lastEventsItems.get(0).getPosition());
        assertEquals(2, (int) lastEventsItems.get(1).getPosition());
        assertEquals(3, (int) lastEventsItems.get(2).getPosition());
        assertEquals(7, (int) lastEventsItems.get(0).getUserId());
        assertEquals(5, (int) lastEventsItems.get(1).getUserId());
        assertEquals(6, (int) lastEventsItems.get(2).getUserId());
    }

    @Test
    public void usersWithEqualsScoreTest() {
        leaderboard.addUser(5, 600);
        leaderboard.addUser(6, 700);
        leaderboard.addUser(7, 700);

        List<PositionChangeEvent.PositionItem> lastEventItems = listener.getLastEvent().getItems();

        assertEquals(3, lastEventItems.size());
        assertTrue(lastEventItems.get(0).getUserId().equals(6) || lastEventItems.get(0).getUserId().equals(7));
        assertTrue(lastEventItems.get(1).getUserId().equals(6) || lastEventItems.get(1).getUserId().equals(7));
        assertEquals(5, (int) lastEventItems.get(2).getUserId());
    }

    @Test
    public void addingUserToTheEndTest() {
        addDefaultUsers();

        leaderboard.addUser(18, 300);

        assertEquals(5, listener.getReceivedEvents().size());

        assertTrue(listener.hasEventFor(18));
        assertTrue(listener.hasEventFor(5));
        assertTrue(listener.hasEventFor(6));
        assertTrue(listener.hasEventFor(7));
        assertTrue(listener.hasEventFor(8));

    }

    @Test
    public void addingUserToTheBeginTest() {
        addDefaultUsers();

        leaderboard.addUser(18, 2000);

        assertEquals(5, listener.getReceivedEvents().size());

        assertTrue(listener.hasEventFor(18));
        assertTrue(listener.hasEventFor(14));
        assertTrue(listener.hasEventFor(15));
        assertTrue(listener.hasEventFor(16));
        assertTrue(listener.hasEventFor(17));
    }

    @Test
    public void addingUserToTheMiddleTest() {
        addDefaultUsers();

        leaderboard.addUser(18, 950);

        assertEquals(5, listener.getReceivedEvents().size());

        assertTrue(listener.hasEventFor(8));
        assertTrue(listener.hasEventFor(9));
        assertTrue(listener.hasEventFor(18));
        assertTrue(listener.hasEventFor(10));
        assertTrue(listener.hasEventFor(11));
    }

    @Test
    public void checkReceivedPositionsTest() {
        addDefaultUsers();

        leaderboard.addUser(18, 950);

        List<PositionChangeEvent> eventForUser = listener.findEventForUser(8);
        assertEquals(1, eventForUser.size());
        List<PositionChangeEvent.PositionItem> items = eventForUser.get(0).getItems();
        assertEquals(18, (int)items.get(0).getUserId());
        assertEquals(9, (int)items.get(1).getUserId());
        assertEquals(8, (int)items.get(2).getUserId());
        assertEquals(7, (int)items.get(3).getUserId());
        assertEquals(6, (int)items.get(4).getUserId());
    }

    @Test
    public void updatePointsTest() {
        addDefaultUsers();

        leaderboard.addPoints(9, 150);

        List<PositionChangeEvent> events = listener.findEventForUser(9);

        assertEquals(1, events.size());
        PositionChangeEvent event = events.get(0);
        List<PositionChangeEvent.PositionItem> items = event.getItems();

        assertEquals(5, items.size());

        assertEquals(12, (int)items.get(0).getUserId());
        assertEquals(11, (int)items.get(1).getUserId());
        assertEquals(9, (int)items.get(2).getUserId());
        assertEquals(10, (int)items.get(3).getUserId());
        assertEquals(8, (int)items.get(4).getUserId());

        assertEquals(1050, (int)items.get(2).getPoints());
    }

    @Test
    public void uniqueUpdateEventTest() {
        addDefaultUsers();

        List<ScoreItem> users = new ArrayList<>();
        users.add(new ScoreItem(18, 750));
        users.add(new ScoreItem(19, 850));
        leaderboard.addUsers(users);

        assertEquals(8, listener.getReceivedEvents().size());
    }

    @Test
    public void fivesFromEndTest() {
        addDefaultUsers();

        leaderboard.addUser(18, 850);

        assertEquals(7, listener.getReceivedEvents().size());

        assertTrue(listener.hasEventFor(5));
        assertTrue(listener.hasEventFor(6));
        assertTrue(listener.hasEventFor(7));
        assertTrue(listener.hasEventFor(8));
        assertTrue(listener.hasEventFor(9));
        assertTrue(listener.hasEventFor(10));
        assertTrue(listener.hasEventFor(18));
    }

    @Test
    public void fivesFromTopTest() {
        addDefaultUsers();

        leaderboard.addUser(18, 1350);

        assertEquals(7, listener.getReceivedEvents().size());

        assertTrue(listener.hasEventFor(12));
        assertTrue(listener.hasEventFor(13));
        assertTrue(listener.hasEventFor(14));
        assertTrue(listener.hasEventFor(15));
        assertTrue(listener.hasEventFor(16));
        assertTrue(listener.hasEventFor(17));
        assertTrue(listener.hasEventFor(18));
    }


    private void addDefaultUsers() {
        leaderboard.addUser(5, 500);
        leaderboard.addUser(6, 600);
        leaderboard.addUser(7, 700);
        leaderboard.addUser(8, 800);
        leaderboard.addUser(9, 900);
        leaderboard.addUser(10, 1000);
        leaderboard.addUser(11, 1100);
        leaderboard.addUser(12, 1200);
        leaderboard.addUser(13, 1300);
        leaderboard.addUser(14, 1400);
        leaderboard.addUser(15, 1500);
        leaderboard.addUser(16, 1600);
        leaderboard.addUser(17, 1700);

        listener.clear();
    }
}

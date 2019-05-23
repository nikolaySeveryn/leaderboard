package nick.leaderboard;

import nick.leaderboard.board.Leaderboard;
import nick.leaderboard.listener.PrintPositionChangeListener;
import nick.leaderboard.thread.AddUserThread;
import nick.leaderboard.thread.UpdatePointsThread;

public class Application {

    public static void main(String[] args) {
        Leaderboard instance = Leaderboard.getInstance();
        instance.subscribe(new PrintPositionChangeListener());

        AddUserThread addUserThread = new AddUserThread();
        addUserThread.start();
        UpdatePointsThread updatePointsThread = new UpdatePointsThread();
        updatePointsThread.start();

    }
}

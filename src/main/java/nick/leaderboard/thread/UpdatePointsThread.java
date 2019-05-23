package nick.leaderboard.thread;

import nick.leaderboard.board.Leaderboard;

import java.util.List;

public class UpdatePointsThread extends BaseThread {

    @Override
    public void run() {
        Leaderboard leaderboard = Leaderboard.getInstance();

        while (true) {
            sleep(nextRandomInt(1000, 4001));

            List<Integer> userIds = leaderboard.getUserIds();
            if (userIds.size() < 1) {
                continue;
            }
            int index = nextRandomInt(0, userIds.size());
            int amount = nextRandomInt(1, 11);
            leaderboard.addPoints(userIds.get(index), amount);
        }
    }
}

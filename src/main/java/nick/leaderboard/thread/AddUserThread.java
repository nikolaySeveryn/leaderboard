package nick.leaderboard.thread;

import nick.leaderboard.board.Leaderboard;
import nick.leaderboard.board.ScoreItem;

import java.util.ArrayList;
import java.util.List;

public class AddUserThread extends BaseThread {

    @Override
    public void run() {
        Leaderboard leaderboard = Leaderboard.getInstance();
        Integer userId = 1;
        while (true) {
            sleep(nextRandomInt(1000, 10001));

            int usersToAddCount = nextRandomInt(1, 5);
            List<ScoreItem> itemList = new ArrayList<>(usersToAddCount);
            for (int i = 0; i < usersToAddCount; ++i) {
                itemList.add(new ScoreItem(userId++, nextRandomInt(100, 351)));
            }
            leaderboard.addUsers(itemList);
        }
    }


}

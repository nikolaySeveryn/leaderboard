package nick.leaderboard.thread;

import java.util.Random;

public class BaseThread extends Thread {

    protected void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println("Sleeping was interrupted");
            System.err.println(e);
        }
    }

    protected int nextRandomInt(int origin, int bound) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(bound - origin) + origin;
    }
}

package nick.leaderboard.board;

class UserAlreadyAddedToScoreboardExeption extends RuntimeException {

    UserAlreadyAddedToScoreboardExeption(String message) {
        super(message);
    }
}

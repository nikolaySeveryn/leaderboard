package nick.leaderboard.board;

class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String message) {
        super(message);
    }
}

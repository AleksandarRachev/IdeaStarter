package ideastarter.ideastarter.util.exception;

public class NotLoggedException extends BaseException {
    public NotLoggedException() {
        super("You are not logged in");
    }
}

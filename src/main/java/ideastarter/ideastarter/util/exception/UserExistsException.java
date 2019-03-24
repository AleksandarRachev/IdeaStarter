package ideastarter.ideastarter.util.exception;

public class UserExistsException extends BaseException {
    public UserExistsException() {
        super("User already exists");
    }
}

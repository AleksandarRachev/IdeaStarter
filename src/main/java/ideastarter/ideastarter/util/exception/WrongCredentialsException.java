package ideastarter.ideastarter.util.exception;

public class WrongCredentialsException extends BaseException {
    public WrongCredentialsException() {
        super("Missing valuable fields");
    }
}

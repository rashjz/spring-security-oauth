package test.app.com.exception;


public class UserNotFoundException  extends Exception  {

    private final int code;

    public UserNotFoundException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public UserNotFoundException(String message, int code) {
        super(message);
        this.code = code;
    }

    public UserNotFoundException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}
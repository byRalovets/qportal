package by.ralovets.qportal.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException() {
        super();
    }

    public UserExistsException(String message) {
        super(message);
    }
}

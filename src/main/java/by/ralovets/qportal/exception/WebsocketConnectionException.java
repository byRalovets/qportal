package by.ralovets.qportal.exception;

public class WebsocketConnectionException extends RuntimeException {

    public WebsocketConnectionException() {
        super();
    }

    public WebsocketConnectionException(String message) {
        super(message);
    }
}

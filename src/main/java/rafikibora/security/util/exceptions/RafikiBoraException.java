package rafikibora.security.util.exceptions;

public class RafikiBoraException extends RuntimeException {
    public RafikiBoraException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public RafikiBoraException(String exMessage) {
        super(exMessage);
    }
}

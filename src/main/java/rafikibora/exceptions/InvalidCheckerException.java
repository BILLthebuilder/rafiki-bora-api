package rafikibora.exceptions;

public class InvalidCheckerException extends RuntimeException{
    public InvalidCheckerException(String message)
    {
        super("Error from RafikiBora: " + message);
    }
}

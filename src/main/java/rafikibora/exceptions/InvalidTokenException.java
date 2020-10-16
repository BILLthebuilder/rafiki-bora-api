package rafikibora.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message)
    {
        super("Error from RafikiBora: " + message);
    }
}
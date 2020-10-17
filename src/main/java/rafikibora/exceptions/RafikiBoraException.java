package rafikibora.exceptions;

public class RafikiBoraException extends RuntimeException
{
    public RafikiBoraException(String message)
    {
        super("Error from RafikiBora: " + message);
    }
}

package rafikibora.exceptions;

public class BadRequestException extends RuntimeException
{
    public BadRequestException(String message)
    {
        super("Error from RafikiBora: " + message);
    }
}

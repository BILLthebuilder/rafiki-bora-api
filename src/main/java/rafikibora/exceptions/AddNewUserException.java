package rafikibora.exceptions;

public class AddNewUserException extends RuntimeException
{
    public AddNewUserException(String message)
    {
        super("Error from RafikiBora: " + message);
    }
}
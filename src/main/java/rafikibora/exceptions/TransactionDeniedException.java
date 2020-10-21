package rafikibora.exceptions;

public class TransactionDeniedException
        extends RuntimeException
{
    public TransactionDeniedException(String message)
    {
        super("Transaction Error: " + message);
    }
}


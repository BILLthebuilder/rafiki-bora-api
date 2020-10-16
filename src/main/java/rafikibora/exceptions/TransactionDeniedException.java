package rafikibora.exceptions;

public class TransactionDeniedException
        extends RuntimeException
{
    public TransactionDeniedException(String message)
    {
        super("Deposit/Sale Transaction Error: " + message);
    }
}


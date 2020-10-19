package rafikibora.dto;

import rafikibora.model.transactions.Transaction;

public class TransactionDto {
    private final String id;
    private final String pan;
    private final String currencyCode;
    private final String amount;
    private final String type;
    private final String date;

    public TransactionDto(Transaction transaction){
        this.id = String.valueOf(transaction.getId());
        this.pan = transaction.getPan();
        this.currencyCode = transaction.getCurrencyCode();
        this.amount = String.valueOf(transaction.getAmountTransaction());
        this.type = transaction.getTransactionType();
        this.date = transaction.getTransactionDate(transaction.getDateTimeTransmission());
    }

    public String getId() {
        return id;
    }

    public String getPan() {
        return pan;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}

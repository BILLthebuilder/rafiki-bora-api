package rafikibora.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"found", "transaction"})
public class SingleTransactionDto {
    private final TransactionDto transaction;

    public SingleTransactionDto(TransactionDto transaction){
        this.transaction= transaction;
    }

    @JsonProperty("transaction")
    public TransactionDto getTransaction() {
        return transaction;
    }
    @JsonProperty("found")
    public boolean isFound(){
        return true;
    }
}

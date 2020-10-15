package rafikibora.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties
public class SendMoneyDto {
    private String amount;
    private String TID;
    private String MID;
    private String currencyCode;
    private String senderAccount;
    private String receiverAccount;
}

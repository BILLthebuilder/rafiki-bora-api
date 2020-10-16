package rafikibora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMoneyRequestDto {
    private String pan;
    private String processingCode;
    private String txnAmount;
    private String transmissionDateTime;
    private String tid;
    private String mid;
    private String receiveMoneyToken;
    private String txnCurrencyCode;
}

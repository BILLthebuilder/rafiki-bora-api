package rafikibora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMoneyResponseDto {
    private String message;
    private String currencyCode;
    private String txnAmount;
    private String authToken;
}

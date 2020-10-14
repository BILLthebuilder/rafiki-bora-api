package rafikibora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMoneyRequestDto {
    private String pan;
    private String pcode;
    private String txnAmount;
    private String transmissionDateTime;
    private String stan;
    private String txnLocalTime;
    private String txnLocalDate;
    private String posEntryMode;
    private String posConditionCode;
    private String tid;
    private String mid;
    private String receiveMoneyToken;
    private String txnCurrencyCode;
    private String srcAccount;
    private String destAccount;
    private String agentAuthToken;

}

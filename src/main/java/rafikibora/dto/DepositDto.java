package rafikibora.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties
public class DepositDto {
    private String merchantPan;
    private String customerPan;
    private String amountTransaction;
    private String DateTimeTransmission;
    private String terminal;
    private String merchant;
    private String currencyCode;
    private String processingCode;
}


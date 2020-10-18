package rafikibora.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties
public class SaleDto {
    private String pan;
    private String amountTransaction;
    private String transmissionDateTime;
    private String terminal;
    private String merchant;
    private String currencyCode;
    private String processingCode;
}

package rafikibora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SignupResponse {
    private responseStatus status;
    private String message;

    public enum responseStatus{
        SUCCESS, FAILED
    }
}

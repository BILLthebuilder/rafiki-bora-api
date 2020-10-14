package rafikibora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private responseStatus status;
    private String message;
    private String authToken;
    private String email;
    private List<?> roles;

    public AuthenticationResponse(responseStatus failed, String message, Object o, Object o1) {
    }

    public enum responseStatus{
        SUCCESS, FAILED
    }
}
package rafikibora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Response {
    private responseStatus status;
    private String message;
    
//    private String authToken;
//    private String email;

    public enum responseStatus{
        SUCCESS, FAILED
    }
}
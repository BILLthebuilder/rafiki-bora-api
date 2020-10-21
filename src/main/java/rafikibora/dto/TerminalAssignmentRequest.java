package rafikibora.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TerminalAssignmentRequest {
    @NotBlank(message = "user email cannot be empty")
    private String email;

    @NotBlank(message = "terminal tid cannot be empty")
    private String tid;
}

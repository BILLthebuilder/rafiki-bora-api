package rafikibora.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TerminalAssignmentRequest {
    @NotBlank(message = "user id cannot be empty")
    private long merchantid;

    @NotBlank(message = "terminal id cannot be empty")
    private long terminalid;
}

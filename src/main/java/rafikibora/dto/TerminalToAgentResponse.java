package rafikibora.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TerminalToAgentResponse {

    @NotBlank(message = "agent email id cannot be empty")
    private String agentEmail;

    @NotBlank(message = " terminal id cannot be empty")
    private String tid;
}

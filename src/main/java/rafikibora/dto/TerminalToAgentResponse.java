package rafikibora.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TerminalToAgentResponse {


    @NotBlank(message = "merchant id cannot be empty")
    private long merchantid;

    @NotBlank(message = "agent id cannot be empty")
    private long agentid;

    @NotBlank(message = "terminal id cannot be empty")
    private long terminalid;
}

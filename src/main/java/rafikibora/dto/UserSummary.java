package rafikibora.dto;

import lombok.Data;
import rafikibora.model.users.Role;

import java.util.Set;

@Data
public class UserSummary {
    private Long userId;
    private String email;
    private Set<Role> roles;
}

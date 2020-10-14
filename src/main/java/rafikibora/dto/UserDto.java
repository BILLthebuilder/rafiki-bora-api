package rafikibora.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String password;
    private String role;
}

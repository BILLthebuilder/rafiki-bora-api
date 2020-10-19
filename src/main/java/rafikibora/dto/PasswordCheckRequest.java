package rafikibora.dto;


import lombok.Data;

@Data
public class PasswordCheckRequest {

    String userPassword;
    String userEmail;
}

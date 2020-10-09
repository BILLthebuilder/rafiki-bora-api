package rafikibora.services;

import org.springframework.http.ResponseEntity;
import rafikibora.dto.LoginRequest;
import rafikibora.dto.LoginResponse;
import rafikibora.dto.UserDto;
import rafikibora.dto.UserSummary;

public interface UserService {
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    void save(UserDto user);


    UserSummary getUserProfile();
}

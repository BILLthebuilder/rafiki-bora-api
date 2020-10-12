package rafikibora.services;

import org.springframework.http.ResponseEntity;
import rafikibora.dto.*;

public interface UserServiceI {
    //ResponseEntity<LoginResponse> login2(LoginRequest loginRequest, String accessToken, String refreshToken);

    //ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) throws Exception;

    ResponseEntity<SignupResponse> save(UserDto user);


    UserSummary getUserProfile();
}

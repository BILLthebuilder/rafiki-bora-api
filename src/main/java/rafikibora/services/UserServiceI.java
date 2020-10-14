package rafikibora.services;

import org.springframework.http.ResponseEntity;
import rafikibora.dto.*;
import rafikibora.model.users.User;

import java.util.Set;

public interface UserServiceI {
    //ResponseEntity<LoginResponse> login2(LoginRequest loginRequest, String accessToken, String refreshToken);

    //ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) throws Exception;

    ResponseEntity<SignupResponse> save(UserDto user);
    ResponseEntity deleteUser(int user);


    UserSummary getUserProfile();

//    List<User> getUserByRole(String roleName);

    Set<User> getUserByRole(String roleName);

}

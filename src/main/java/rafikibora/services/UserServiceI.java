package rafikibora.services;

import org.springframework.http.ResponseEntity;
import rafikibora.dto.*;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.users.User;

import java.util.List;
import java.util.Set;

public interface UserServiceI {

    ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) throws Exception;

    Set<User> getUserByRole(String roleName);

    User findByName(String name);

    User deleteUser(long id);

    List<User> viewUsers();

}

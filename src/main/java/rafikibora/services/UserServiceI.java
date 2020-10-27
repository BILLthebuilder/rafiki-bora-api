package rafikibora.services;

import org.springframework.http.ResponseEntity;
import rafikibora.dto.*;
import rafikibora.model.account.Account;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.users.User;

import java.util.List;
import java.util.Set;

public interface UserServiceI {

    ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) throws Exception;

    List<User> getUserByRole(String roleName);

    User findByName(String name);

//    User deleteUser(String email);

    List<User> viewUsers();

    void addAgent(User user);

    void  addUser(User user);

    User approveUser(String email);

     User updateUser(User user, int userid);

   void  assignTerminals(TerminalAssignmentRequest terminalAssignmentRequest);

   void assignTerminalsToAgent (TerminalToAgentResponse terminalToAgentResponse);





}

package rafikibora.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.ListSystemUser;
import rafikibora.dto.SystemUser;
import rafikibora.dto.TerminalAssignmentRequest;
import rafikibora.dto.TerminalToAgentResponse;
import rafikibora.exceptions.AddNewUserException;
import rafikibora.exceptions.BadRequestException;
import rafikibora.model.users.User;
import rafikibora.services.UserService;
import rafikibora.services.UserServiceI;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/users")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private UserService userService;

    @PostMapping("/createuser")
    public ResponseEntity<?> addUser(@RequestBody User user){
        if(user.getRole() == null)
            throw new BadRequestException("User has to have an assigned role");
        try {
            userServiceI.addUser(user);
        } catch (Exception ex) {
            throw new AddNewUserException(ex.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Returns the User record for the currently authenticated user based off of the supplied access token
     * <br>Example: <a href="http://localhost:8080/users/profile">http://localhost:2019/users/getuserinfo</a>
     *
     * @param authentication The authenticated user object provided by Spring Security
     * @return JSON of the current user. Status of OK
     * @see UserService#findByName(String) UserService.findByName(authenticated user)
     */
    @GetMapping(value = "/user/profile",produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
        User user = userServiceI.findByName(authentication.getName());
        return new ResponseEntity<>(user,
                HttpStatus.OK);
    }

    @PostMapping("/user/approve/{email}")
    public ResponseEntity<?> approve(@PathVariable("email") String email){

        User approvedUser = userServiceI.approveUser(email);

        return new ResponseEntity<>(approvedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable @Param("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity("UserAccount Deleted", HttpStatus.OK);
    }

    @GetMapping("/{roleName}")
    public Set<User> findUserByRoles(@PathVariable("roleName") String roleName) {
        return userServiceI.getUserByRole(roleName);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void findAllUsers(HttpServletResponse response) throws IOException {
        List<User> users = userServiceI.viewUsers();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNodes = mapper.createObjectNode();
        String data = "";

        if(userServiceI.viewUsers().isEmpty()){
            jsonNodes.put("found", false);
            data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNodes);
        } else{
            data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.buildUserListJson(users));
        }
        response.getWriter().println(data);
    }

    //find user by the Id
    @GetMapping("user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable @Param("id") int id) {
        return (ResponseEntity<User>) userService.getUserById(id);
    }

    @PostMapping("/addagent")
    public void addAgent (@RequestBody User user){
         userServiceI.addAgent(user);
    }


    @PostMapping(value = "/assignmerchantterminal")
    public ResponseEntity<?> assignmerchantterminal(@RequestBody TerminalAssignmentRequest terminalAssignmentRequest) throws Exception {
       userServiceI.assignTerminals(terminalAssignmentRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping(value = "/{id}", consumes = {"application/json"})
    public ResponseEntity<?> updateAccount(@RequestBody User user, @PathVariable int id) {
        userServiceI.updateUser(user, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/agenttoterminal")
        public ResponseEntity<?> terminalToAgent(@RequestBody TerminalToAgentResponse terminalToAgentResponse) throws Exception {
        userServiceI.assignTerminalsToAgent(terminalToAgentResponse);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("id/{id}")
    public User findById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }


    @GetMapping("/unAssignedAgents")
    public List<User> unAssignedAgents(){
        return userService.unAssignedAgents();
    }


    private ListSystemUser buildUserListJson(List<User> users){
        return new ListSystemUser(users.stream().map(user -> new SystemUser(user)).collect(Collectors.toList()));
    }

}


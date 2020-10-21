package rafikibora.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.dto.*;
import rafikibora.exceptions.InvalidCheckerException;
import rafikibora.exceptions.ResourceNotFoundException;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.users.Role;
import rafikibora.model.users.User;
import rafikibora.model.users.UserRoles;
import rafikibora.repository.RoleRepository;
import rafikibora.repository.TerminalRepository;
import rafikibora.repository.UserRepository;
import rafikibora.security.util.exceptions.RafikiBoraException;

import javax.persistence.EntityExistsException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserServiceI {

    private final UserRepository userRepository;
    private final TerminalRepository terminalRepository;
    private final RoleRepository roleRepository;
    private final JwtProviderI jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    //it is where we store details of the present security context of the application
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.findByName(authentication.getName());
        return user;
    }

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) {
        AuthenticationResponse authResponse;
        try {
            authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        } catch (Exception ex) {
            authResponse = new AuthenticationResponse(AuthenticationResponse.responseStatus.FAILED, ex.getMessage(), null, null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
        List<?> userRoles = userDetails.getAuthorities().stream().map(s ->
                new SimpleGrantedAuthority(s.getAuthority())).
                filter(Objects::nonNull).
                collect(Collectors.toList());
        String token = jwtProvider.generateToken(userDetails);
        boolean validateToken = jwtProvider.validateToken(token);
        if (!validateToken) {
            jwtProvider.generateToken(userDetails);
        }
        authResponse = new AuthenticationResponse(AuthenticationResponse.responseStatus.SUCCESS, "Successful Login", token, loginRequest.getEmail(), userRoles);
        return ResponseEntity.ok().body(authResponse);
    }

    //test validity of credentialsi
    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new RafikiBoraException("User is Disabled");
        } catch (BadCredentialsException e) {
            throw new RafikiBoraException("Invalid Credentials");
        }
    }

    //find user by Id
    public ResponseEntity<?> getUserById(int id) {
    Response response;
    Optional<User> optional = Optional.ofNullable(userRepository.findById(id));
    User user = null;
    if (optional.isPresent()){
        user = optional.get();
    } else {
        response = new Response(Response.responseStatus.FAILED," User not found for id :: " + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    return ResponseEntity.status(HttpStatus.OK).body(user);
}

    //soft delete user
    @Transactional
    public ResponseEntity<?> deleteUser(int id) {
        User user = new User();
        if (user!=null) {
            userRepository.deleteById((long) id);
            // return "account disabled";
            return new ResponseEntity("UserAccount Deleted", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User with id   " + id + " Not Found");
        }
    }

    //find user by name
    @Override
    public User findByName(String name) {
        User user = userRepository.findByEmail(name.toLowerCase());
        if (user == null) {
            throw new ResourceNotFoundException("User with email " + name + " not found!");
        }
        return user;
    }

    //list users of specific roles
    @Override
    public Set<User> getUserByRole(String roleName) {

        Set<User> users = userRepository.findByRoles_Role_RoleNameContainingIgnoreCase(roleName);

        return users;
    }

   //list user by id
    public User getUserById(long id) {

        User user = userRepository.findById(id);
        log.info("user:",user);
        return user;
    }

    //list all users
    @Override
    public List<User> viewUsers() {
        return userRepository.findAll();
    }

    //Setting user Maker
    @Transactional
    public void addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException("Email already exists");
        }
        User currentUser = getCurrentUser();
        user.setUserMaker(currentUser);

        Role role = null;
        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            role = roleRepository.findByRoleName("ADMIN");
        }
        if (user.getRole().equalsIgnoreCase("MERCHANT")) {
            role = roleRepository.findByRoleName("MERCHANT");
        }
        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            role = roleRepository.findByRoleName("CUSTOMER");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(new UserRoles(user, role));
        userRepository.save(user);


    }

    //Setting user Checker
    @Transactional
    public User approveUser(String email) {
        User currentUser = getCurrentUser();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("This user does not exist");
        }
        // A user cannot be approved by the same Admin who created them
        if (currentUser == user.getUserMaker()) {
            throw new InvalidCheckerException("You cannot approve this user!");
        }
        // if user has Merchant role generate MID and assign
        boolean merchant = false;
        Set<UserRoles> retrievedRoles = user.getRoles();
        for (UserRoles userRole : retrievedRoles) {
            if (userRole.getRole().getRoleName().equalsIgnoreCase("MERCHANT")) {
                merchant = true;
                break;
            }
        }
        if (merchant == true) {
            String mid = UUID.randomUUID().toString().substring(0, 16);
            user.setMid(mid);
        }

        user.setUserChecker(currentUser);
        user.setStatus(true);
        return userRepository.save(user);
    }

    //Make merchant On board their Agents
    @Override
    public void addAgent(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException("Email already exists");
        }
        User currentUser = getCurrentUser();
        Role role = null;
        if (user.getRole().equalsIgnoreCase("AGENT")) {
            role = roleRepository.findByRoleName("AGENT");
        }
            Set<UserRoles> retrievedRoles = currentUser.getRoles();

            for (UserRoles userRole : retrievedRoles) {
                if (userRole.getRole().getRoleName().equalsIgnoreCase("MERCHANT")) {

                    user.setStatus(true);
                    user.getRoles().add(new UserRoles(user, role));
                    user.setUserMaker(currentUser);
                    user.setUserChecker(null);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userRepository.save(user);

                }
            }

        }

    //allow uses to update their information
    public User updateUser(User user, int userid) {
        User existinguser = userRepository.findById(userid);
        if (existinguser == null) {
            UserService.log.error("User " + userid + " Not Found");
        }

        if (user.getEmail() != null) {
            existinguser.setEmail(user.getEmail());
        }

        if (user.getPhoneNo() != null) {
            existinguser.setPhoneNo(user.getPhoneNo());
        }

        if (user.getFirstName() != null) {
            existinguser.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            existinguser.setLastName(user.getLastName());
        }

        if (user.getPassword() != null) {
            existinguser.setPassword(user.getPassword());
        }


        return userRepository.save(existinguser);
    }

    //assign terminals to Merchants
    public void assignTerminals(TerminalAssignmentRequest terminalAssignmentRequest) {

        long merchantID = terminalAssignmentRequest.getMerchantid();
        long terminalID = terminalAssignmentRequest.getTerminalid();


        User merchant = userRepository.findById(merchantID);
        Terminal terminal = terminalRepository.findById(terminalID);

        try {
            terminal.setMid(merchant);
            terminalRepository.save(terminal);

        } catch (Exception ex) {
            log.error("Error assigning terminals: " + ex.getMessage());
            throw ex;
        }

    }

    //assign terminals from merchants to agents
    public void assignTerminalsToAgent(TerminalToAgentResponse terminalToAgentResponse) {
        User merchant = getCurrentUser();
        User agent= null;
        Terminal terminal;

        long merchantid = terminalToAgentResponse.getMerchantid();
        long agentid = terminalToAgentResponse.getAgentid();
        long terminalid = terminalToAgentResponse.getTerminalid();

         merchant = userRepository.findById(merchantid);
         agent=userRepository.findById(agentid);
         terminal = terminalRepository.findById(terminalid);

         if(merchant==agent.getUserMaker()&& merchant==terminal.getMid()){
             agent.getAssignedTerminals().add(terminal);
             userRepository.save(agent);
         }
    }


}
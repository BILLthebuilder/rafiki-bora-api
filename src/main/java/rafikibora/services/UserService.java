package rafikibora.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import rafikibora.exceptions.BadRequestException;
import rafikibora.exceptions.InvalidCheckerException;
import rafikibora.exceptions.ResourceNotFoundException;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.users.Role;
import rafikibora.model.users.User;
import rafikibora.model.users.UserRoles;
import rafikibora.repository.RoleRepository;
import rafikibora.repository.UserRepository;
import rafikibora.security.util.exceptions.RafikiBoraException;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserServiceI {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtProviderI jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.findByName(authentication.getName());
        ;
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
        authResponse = new AuthenticationResponse(AuthenticationResponse.responseStatus.SUCCESS, "Successful Login",token,loginRequest.getEmail(), userRoles);
        return ResponseEntity.ok().body(authResponse);
    }

    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new RafikiBoraException("User is Disabled");
        } catch (BadCredentialsException e) {
            throw new RafikiBoraException("Invalid Credentials");
        }
    }


    @Override
    public User deleteUser(long id) {
        //User currentUser = getCurrentUser();
        User user = userRepository.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException("This user does not exist");
        }

        user.setDeleted(true);

        return user;
    }

    @Override
    public User findByName(String name) {
        User user = userRepository.findByEmail(name.toLowerCase());
        if (user == null) {
            throw new ResourceNotFoundException("User with email " + name + " not found!");
        }
        return user;
    }

    @Override
    public Set<User> getUserByRole(String roleName) {

        Set<User> users = userRepository.findByRoles_Role_RoleNameContainingIgnoreCase(roleName);

        return users;
    }

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
        if (user.getRole().equalsIgnoreCase("AGENT")) {
            role = roleRepository.findByRoleName("AGENT");
        }
        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            role = roleRepository.findByRoleName("CUSTOMER");
        }

        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getRoles().add(new UserRoles(user, role));
            userRepository.save(user);
        }

    }

    @Override
    public List<User> viewUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User approveUser(String email) {
        User currentUser = getCurrentUser();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("This user does not exist");
        }
        // a user can only be approved by a user who is an admin
        // Todo
        // A user cannot be approved by the same Admin who created them
        if (currentUser == user.getUserMaker()) {
            throw new InvalidCheckerException("You cannot approve this user!"); }
        // if user has Merchant role generate MID and assign
        // Todo
        if (user.getRoles().equals("MERCHANT")){
            String mid = UUID.randomUUID().toString().substring(0,16);
            System.out.println(mid);
            user.setMid(mid);
        }
        user.setUserChecker(currentUser);
        user.setStatus(true);
        return userRepository.save(user);
    }



    // terminal service
//    @Transactional
//    public void addTerminal(Terminal terminal) {
//        User currentUser = getCurrentUser();
//        // Todo
//    }
//
//    @Override
//    public Terminal approveTerminal(String serialNo) {
//        User currentUser = getCurrentUser();
//        // Todo
//
//        return null;
//    }

}
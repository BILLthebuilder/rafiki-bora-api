package rafikibora.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.SignupResponse;
import rafikibora.model.users.User;
import rafikibora.services.UserService;
import rafikibora.services.UserServiceI;
import rafikibora.dto.UserDto;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/auth")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<SignupResponse> signUp(@RequestBody UserDto user) {
        return userServiceI.save(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteAccount(@PathVariable int id) {
        return userServiceI.deleteUser(id);
    }

    @GetMapping("/{roleName}")
    public Set<User> findUserByRoles(@PathVariable("roleName") String roleName) {
        return userService.getUserByRole(roleName);
    }

}


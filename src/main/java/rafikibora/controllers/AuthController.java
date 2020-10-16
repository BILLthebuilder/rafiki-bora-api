package rafikibora.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafikibora.dto.AuthenticationResponse;
import rafikibora.dto.LoginRequest;
import rafikibora.services.UserServiceI;

 @RestController
 @RequestMapping("api/auth")
 @AllArgsConstructor
 @Slf4j
 public class AuthController {

     private final UserServiceI userServiceI;

     @PostMapping(value = "/login")
     public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
         return userServiceI.login(loginRequest);
     }

 }

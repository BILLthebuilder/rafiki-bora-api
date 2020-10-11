package rafikibora.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.LoginRequest;
import rafikibora.dto.LoginResponse;
import rafikibora.dto.UserSummary;
import rafikibora.security.util.SecurityCipher;
import rafikibora.services.UserService;

import javax.validation.Valid;

 @RestController
 //@RequestMapping("api/auth")
 @AllArgsConstructor
 public class AuthController {

     private final AuthenticationManager authenticationManager;


     private final UserService userService;



     @GetMapping("/profile")
     public ResponseEntity<UserSummary> me() {
         return ResponseEntity.ok(userService.getUserProfile());
     }

     @PostMapping(value = "api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<LoginResponse> login(
             @CookieValue(name = "accessToken", required = false) String accessToken,
             @CookieValue(name = "refreshToken", required = false) String refreshToken,
             @Valid @RequestBody LoginRequest loginRequest
     ) {
         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
         SecurityContextHolder.getContext().setAuthentication(authentication);

         String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
         String decryptedRefreshToken = SecurityCipher.decrypt(refreshToken);
         System.out.println(decryptedAccessToken);
         return userService.login(loginRequest, decryptedAccessToken, decryptedRefreshToken);
     }

     @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<LoginResponse> refreshToken(@CookieValue(name = "accessToken", required = false) String accessToken,
                                                       @CookieValue(name = "refreshToken", required = false) String refreshToken) {
         String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
         String decryptedRefreshToken = SecurityCipher.decrypt(refreshToken);
         return userService.refresh(decryptedAccessToken, decryptedRefreshToken);
     }
 }

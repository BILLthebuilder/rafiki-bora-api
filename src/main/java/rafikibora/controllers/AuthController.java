package rafikibora.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import rafikibora.dto.*;
import rafikibora.model.users.User;
import rafikibora.services.UserServiceI;

 @RestController
 @RequestMapping("api/auth")
 @AllArgsConstructor
 @Slf4j
 public class AuthController {

     private final AuthenticationManager authenticationManager;

     private final UserServiceI userServiceI;


     @PostMapping(value = "/login")
     public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
         return userServiceI.login(loginRequest);
     }




//     @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<LoginResponse> refreshToken(@CookieValue(name = "accessToken", required = false) String accessToken,
//                                                       @CookieValue(name = "refreshToken", required = false) String refreshToken) {
//         String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
//         String decryptedRefreshToken = SecurityCipher.decrypt(refreshToken);
//         return userServiceI.refresh(decryptedAccessToken, decryptedRefreshToken);
//     }
//
//     @PostMapping(value = "api/auth/login2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<LoginResponse> login2(
//             @CookieValue(name = "accessToken", required = false) String accessToken,
//             @CookieValue(name = "refreshToken", required = false) String refreshToken,
//             @Valid @RequestBody LoginRequest loginRequest
//     ) {
//         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//         SecurityContextHolder.getContext().setAuthentication(authentication);
//
//         String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
//         String decryptedRefreshToken = SecurityCipher.decrypt(refreshToken);
////         log.info(decryptedAccessToken);
//         return userServiceI.login2(loginRequest, decryptedAccessToken, decryptedRefreshToken);
//     }

 }

package rafikibora.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.dto.*;
import rafikibora.model.users.User;
import rafikibora.repository.UserRepository;
import rafikibora.security.util.CookieUtil;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final TokenProvider tokenProvider;


    private final CookieUtil cookieUtil;


    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void save(UserDto user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNo(user.getPhoneNo());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        //newUser.setPassword(user.getPassword());
        log.info("***********************");
        log.info("The Data here is:", user);
        log.info("***********************");
        userRepository.save(newUser);
    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

     @Override
     public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken) {
         String email = loginRequest.getEmail();
         User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found with email " + email));

         Boolean accessTokenValid = tokenProvider.validateToken(accessToken);
         Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

         HttpHeaders responseHeaders = new HttpHeaders();
         Token newAccessToken;
         Token newRefreshToken;
         if (!accessTokenValid && !refreshTokenValid) {
             newAccessToken = tokenProvider.generateAccessToken(user.getEmail());
             newRefreshToken = tokenProvider.generateRefreshToken(user.getEmail());
             addAccessTokenCookie(responseHeaders, newAccessToken);
             addRefreshTokenCookie(responseHeaders, newRefreshToken);
         }

         if (!accessTokenValid && refreshTokenValid) {
             newAccessToken = tokenProvider.generateAccessToken(user.getEmail());
             addAccessTokenCookie(responseHeaders, newAccessToken);
         }

         if (accessTokenValid && refreshTokenValid) {
             newAccessToken = tokenProvider.generateAccessToken(user.getEmail());
             newRefreshToken = tokenProvider.generateRefreshToken(user.getEmail());
             addAccessTokenCookie(responseHeaders, newAccessToken);
             addRefreshTokenCookie(responseHeaders, newRefreshToken);
         }

         LoginResponse loginResponse = new LoginResponse(LoginResponse.SuccessFailure.SUCCESS, "Auth successful. Tokens are created in cookie.");
         return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);

     }

     @Override
     public ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken) {
         Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
         if (!refreshTokenValid) {
             throw new IllegalArgumentException("Refresh Token is invalid!");
         }

         String currentUserEmail = tokenProvider.getUsernameFromToken(accessToken);

         Token newAccessToken = tokenProvider.generateAccessToken(currentUserEmail);
         HttpHeaders responseHeaders = new HttpHeaders();
         responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(newAccessToken.getTokenValue(), newAccessToken.getDuration()).toString());

         LoginResponse loginResponse = new LoginResponse(LoginResponse.SuccessFailure.SUCCESS, "Successful Login");
         return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
     }

     @Override
     public UserSummary getUserProfile() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

         User user = userRepository.findByEmail(customUserDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found with email " + customUserDetails.getUsername()));
         return user.toUserSummary();
     }

}

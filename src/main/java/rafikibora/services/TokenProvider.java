//package rafikibora.services;
//
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import rafikibora.dto.Token;
// import io.jsonwebtoken.*;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import rafikibora.model.users.User;
//
// import java.time.LocalDateTime;
// import java.time.ZoneId;
// import java.util.Date;
// import java.util.Objects;
// import java.util.stream.Collectors;
//
//@Service
// public class TokenProvider implements TokenProviderI {
//
//    @Value("${rafiki-bora.auth.tokenSecret}")
//    private String tokenSecret;
//
//    @Value("${rafiki-bora.auth.tokenExpirationMsec}")
//    private Long tokenExpirationMsec;
//
//    @Value("${rafiki-bora.auth.refreshTokenExpirationMsec}")
//    private Long refreshTokenExpirationMsec;
//
//     @Override
//     public Token generateAccessToken(User user) {
//         Claims claims = Jwts.claims().setSubject(user.getEmail());
//         //claims.put("role", "ROLE_ADMIN");
//         claims.put("role", user.getRoles().stream().map(s -> new SimpleGrantedAuthority(s.getRoleName())).filter(Objects::nonNull).collect(Collectors.toList()));
//         Date now = new Date();
//         Long duration = now.getTime() + tokenExpirationMsec;
//         Date expiryDate = new Date(duration);
//         String token = Jwts.builder()
//                 .setClaims(claims)
//                 .setIssuedAt(now)
//                 .setExpiration(expiryDate)
//                 .signWith(SignatureAlgorithm.HS512, tokenSecret)
//                 .compact();
//         return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
//     }
//
//     @Override
//     public Token generateRefreshToken(User user) {
//         Claims claims = Jwts.claims().setSubject(user.getEmail());
//         //claims.put("role", "ROLE_ADMIN");
//         claims.put("role", user.getRoles().stream().map(s -> new SimpleGrantedAuthority(s.getRoleName())).filter(Objects::nonNull).collect(Collectors.toList()));
//         Date now = new Date();
//         Long duration = now.getTime() + refreshTokenExpirationMsec;
//         Date expiryDate = new Date(duration);
//         String token = Jwts.builder()
//                 .setClaims(claims)
//                 .setIssuedAt(now)
//                 .setExpiration(expiryDate)
//                 .signWith(SignatureAlgorithm.HS512, tokenSecret)
//                 .compact();
//         return new Token(Token.TokenType.REFRESH, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
//     }
//
//     @Override
//     public String getUsernameFromToken(String token) {
//         Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
//         return claims.getSubject();
//     }
//
//     @Override
//     public LocalDateTime getExpiryDateFromToken(String token) {
//         Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
//         return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
//     }
//
//     @Override
//     public boolean validateToken(String token) {
//         try {
//             Jwts.parser().setSigningKey(tokenSecret).parse(token);
//             return true;
//         } catch (SignatureException ex) {
//             ex.printStackTrace();
//         } catch (MalformedJwtException ex) {
//             ex.printStackTrace();
//         } catch (ExpiredJwtException ex) {
//             ex.printStackTrace();
//         } catch (UnsupportedJwtException ex) {
//             ex.printStackTrace();
//         } catch (IllegalArgumentException ex) {
//             ex.printStackTrace();
//         }
//         return false;
//     }
// }

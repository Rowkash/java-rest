package server.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Value("${auth.jwtSecret}")
  private String secret;

  @Value("${auth.jwtExpiration}")
  private long expiresInMs;

  private SecretKey getSigningKey() {
    try {
      return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    } catch (Exception e) {
      System.err.println("Unable to get signing key from secret");
      System.err.print(e.getMessage());
      return null;
    }
  }

  //    public String generateToken(long subject, Map<String, Object> claims) {
  //        String jti = UUID.randomUUID().toString();
  //        return Jwts.builder()
  //                .subject(String.valueOf(subject))
  //                .id(jti)
  //                .claims(claims == null ? Map.of() : claims)
  //                .issuedAt(Date.from(Instant.now()))
  //                .expiration(new Date(System.currentTimeMillis() + expiresInMs))
  //                .signWith(getSigningKey(), Jwts.SIG.HS256)
  //                .compact();
  //    }

  public String generateToken(JwtUserData userData) {
    String jti = UUID.randomUUID().toString();
    return Jwts.builder()
        //                .subject(String.valueOf(userData.id()))
        .id(jti)
        .claim("userId", userData.id())
        .claim("userEmail", userData.email())
        //                .claims(claims == null ? Map.of() : claims)
        .issuedAt(Date.from(Instant.now()))
        .expiration(new Date(System.currentTimeMillis() + expiresInMs))
        .signWith(getSigningKey(), Jwts.SIG.HS256)
        .compact();
  }

  public Claims getTokenPayload(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      System.out.println("JWT validation failed: " + e.getMessage());
      return false;
    }
  }

  public JwtUserData extractUserFromToken(String token) {
    Claims claims = getTokenPayload(token);

    //        Long id = Long.valueOf(claims.getSubject());
    Long id = claims.get("userId", Long.class);
    String email = claims.get("userEmail", String.class);

    return new JwtUserData(id, email);
  }
}

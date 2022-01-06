package br.com.ioasys.imdbservice.config.security;

import br.com.ioasys.imdbservice.domain.UserModel;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtProvider {
  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private int jwtExpiration;

  public String generate(Authentication authentication) {
    UserModel userPrincipal = (UserModel) authentication.getPrincipal();
    final String roles =
        userPrincipal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    return Jwts.builder()
        .setSubject((userPrincipal.getUserId().toString()))
        .claim("roles", roles)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getSubject(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validate(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (SignatureException exception) {
      log.error("Invalid JWT signature: {}", exception.getMessage());
    } catch (MalformedJwtException exception) {
      log.error("Invalid JWT token: {}", exception.getMessage());
    } catch (ExpiredJwtException exception) {
      log.error("JWT token is expired: {}", exception.getMessage());
    } catch (UnsupportedJwtException exception) {
      log.error("JWT token is unsupported: {}", exception.getMessage());
    } catch (IllegalArgumentException exception) {
      log.error("JWT claims string is empty: {}", exception.getMessage());
    }
    return false;
  }
}

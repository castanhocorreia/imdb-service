package br.com.ioasys.imdbservice.config.security;

import br.com.ioasys.imdbservice.config.security.impl.UserDetailsServiceImpl;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Log4j2
public class AuthJwtFilter extends OncePerRequestFilter {
  @Autowired JwtProvider jwtProvider;

  @Autowired UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwtStr = getTokenHeader(request);
      if (jwtStr != null && jwtProvider.validate(jwtStr)) {
        String userId = jwtProvider.getSubject(jwtStr);
        UserDetails userDetails = userDetailsService.loadUserById(UUID.fromString(userId));
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set User Authentication: {}", e);
    }
    filterChain.doFilter(request, response);
  }

  private String getTokenHeader(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }
}

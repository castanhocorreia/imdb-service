package br.com.ioasys.imdbservice.config.security;

import br.com.ioasys.imdbservice.domain.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthCurrentUserService {
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public UserModel getCurrentUser() {
    return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}

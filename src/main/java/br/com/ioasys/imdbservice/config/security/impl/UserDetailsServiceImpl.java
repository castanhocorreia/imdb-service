package br.com.ioasys.imdbservice.config.security.impl;

import br.com.ioasys.imdbservice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetails loadUserById(UUID userId) throws AuthenticationCredentialsNotFoundException {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () ->
                new UsernameNotFoundException(String.format("User with id %s not found", userId)));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsername(username)
        .orElseThrow(
            () ->
                new UsernameNotFoundException(
                    String.format("User with username %s not found", username)));
  }
}

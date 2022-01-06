package br.com.ioasys.imdbservice.controller;

import br.com.ioasys.imdbservice.config.security.JwtProvider;
import br.com.ioasys.imdbservice.data.JwtData;
import br.com.ioasys.imdbservice.data.UserData;
import br.com.ioasys.imdbservice.domain.UserModel;
import br.com.ioasys.imdbservice.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;
  private final UserService userService;

  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager,
      JwtProvider jwtProvider,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtProvider = jwtProvider;
    this.userService = userService;
  }

  @PostMapping(path = "/signup")
  public ResponseEntity<Object> store(
      @RequestBody
          @Validated(UserData.UserView.SignUp.class)
          @JsonView(UserData.UserView.SignUp.class)
          UserData userData) {
    log.info("New POST request to create user with the following data: {}", userData.toString());
    if (userService.checkUsernameUnavailability(userData.getUsername())) {
      log.error("CONFLICT: email address {} is already taken", userData.getUsername());
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(String.format("Username %s is already taken", userData.getUsername()));
    }
    UserModel userModel = userService.create(userData);
    log.info("New user created successfully with id {}", userModel.getUserId());
    return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtData> authenticateUser(
      @RequestBody
          @Validated(UserData.UserView.Login.class)
          @JsonView(UserData.UserView.Login.class)
          UserData userData) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userData.getUsername(), userData.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generate(authentication);
    return ResponseEntity.ok(new JwtData(jwt));
  }
}

package br.com.ioasys.imdbservice.controller;

import br.com.ioasys.imdbservice.data.UserData;
import br.com.ioasys.imdbservice.domain.UserModel;
import br.com.ioasys.imdbservice.repository.SpecTemplate;
import br.com.ioasys.imdbservice.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {
  private static final String USER_NOT_FOUND = "User with id %s not found";
  private static final String USER_UPDATED_SUCCESSFULLY = "User with id {} updated successfully";

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "/{userId}")
  public ResponseEntity<Object> show(@PathVariable UUID userId) {
    Optional<UserModel> userOptional = userService.find(userId);
    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format(USER_NOT_FOUND, userId));
    }
    UserModel user = userOptional.get();
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @GetMapping()
  public ResponseEntity<Page<UserModel>> index(
      SpecTemplate.UserSpec spec,
      @PageableDefault(sort = "createdDate", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<UserModel> userPage = userService.list(spec, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(userPage);
  }

  @PutMapping(path = "/{userId}")
  public ResponseEntity<Object> update(
      @PathVariable UUID userId,
      @RequestBody
          @Validated(UserData.UserView.GeneralUpdate.class)
          @JsonView(UserData.UserView.GeneralUpdate.class)
          UserData userData) {
    log.info(
        "New PUT request to update user with id {} with the followiing data {}",
        userId,
        userData.toString());
    Optional<UserModel> userOptional = userService.find(userId);
    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format(USER_NOT_FOUND, userId));
    }
    UserModel user = userService.update(userData, userOptional.get());
    log.info(USER_UPDATED_SUCCESSFULLY, user.getUserId());
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @PutMapping(path = "/{userId}/password")
  public ResponseEntity<Object> updatePassword(
      @PathVariable UUID userId,
      @RequestBody
          @Validated(UserData.UserView.PasswordUpdate.class)
          @JsonView(UserData.UserView.PasswordUpdate.class)
          UserData userData) {
    log.info("New PUT request to update password of user with id {}", userId);
    Optional<UserModel> userOptional = userService.find(userId);
    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format(USER_NOT_FOUND, userId));
    }
    UserModel user = userOptional.get();
    if (!user.getPassword().equals(userData.getPreviousPassword())) {
      log.info("Mismatched previous password for user with id {}", user.getUserId());
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Mismatched previous password");
    }
    userService.update(userData, user);
    log.info(USER_UPDATED_SUCCESSFULLY, user.getUserId());
    return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
  }

  @DeleteMapping(path = "/{userId}")
  public ResponseEntity<Object> delete(@PathVariable UUID userId) {
    log.info("New DELETE request for user with id {}", userId);
    Optional<UserModel> userOptional = userService.find(userId);
    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format(USER_NOT_FOUND, userId));
    }
    userService.delete(userOptional.get());
    log.info("User with id {} deleted successfully", userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(String.format("User with id %s deleted successfully", userId));
  }
}

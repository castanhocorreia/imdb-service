package br.com.ioasys.imdbservice.data;

import br.com.ioasys.imdbservice.data.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserData {
  private UUID userId;

  @JsonView({UserView.GeneralUpdate.class, UserView.RegularSignUp.class})
  @NotBlank(
      groups = {UserView.GeneralUpdate.class, UserView.RegularSignUp.class},
      message = "Ensure that the fullName included in the request are not blank")
  private String fullName;

  @JsonView({UserView.GeneralUpdate.class, UserView.RegularSignUp.class})
  @UsernameConstraint(
      groups = {UserView.GeneralUpdate.class, UserView.RegularSignUp.class},
      message = "Ensure that the username included in the request are correct")
  private String username;

  @JsonView({UserView.RegularSignUp.class, UserView.PasswordUpdate.class})
  @NotBlank(
      groups = {UserView.RegularSignUp.class, UserView.PasswordUpdate.class},
      message = "Ensure that the password included in the request are not blank")
  @Size(
      groups = {UserView.RegularSignUp.class, UserView.PasswordUpdate.class},
      min = 6,
      message = "The new password must contain the minimum number of 6 characters")
  private String password;

  @JsonView({UserView.PasswordUpdate.class})
  @NotBlank(
      groups = {UserView.PasswordUpdate.class},
      message = "Ensure that the previous password included in the request are not blank")
  @Size(
      groups = {UserView.PasswordUpdate.class},
      min = 6,
      message = "The new password must contain the minimum number of 6 characters")
  private String previousPassword;

  public interface UserView {
    interface GeneralUpdate {}

    interface RegularSignUp {}

    interface PasswordUpdate {}
  }
}

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

  @JsonView({UserView.Update.class, UserView.SignUp.class})
  @NotBlank(
      groups = {UserView.Update.class, UserView.SignUp.class},
      message = "Ensure that the fullName included in the request are not blank")
  private String fullName;

  @JsonView({UserView.Update.class, UserView.Login.class, UserView.SignUp.class})
  @UsernameConstraint(
      groups = {UserView.Update.class, UserView.Login.class, UserView.SignUp.class},
      message = "Ensure that the username included in the request are correct")
  private String username;

  @JsonView({UserView.SignUp.class, UserView.Login.class, UserView.Password.class})
  @NotBlank(
      groups = {UserView.SignUp.class, UserView.Login.class, UserView.Password.class},
      message = "Ensure that the password included in the request are not blank")
  @Size(
      groups = {UserView.SignUp.class, UserView.Login.class, UserView.Password.class},
      min = 6,
      message = "The password must contain the minimum number of 6 characters")
  private String password;

  @JsonView({UserView.Password.class})
  @NotBlank(
      groups = {UserView.Password.class},
      message = "Ensure that the previous password included in the request are not blank")
  @Size(
      groups = {UserView.Password.class},
      min = 6,
      message = "The new password must contain the minimum number of 6 characters")
  private String previousPassword;

  public interface UserView {
    interface Login {}

    interface Password {}

    interface SignUp {}

    interface Update {}
  }
}

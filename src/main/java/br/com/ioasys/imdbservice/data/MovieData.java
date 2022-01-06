package br.com.ioasys.imdbservice.data;

import br.com.ioasys.imdbservice.domain.GenreType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieData {
  private UUID movieId;

  @NotBlank(
      groups = {UserData.UserView.Update.class, UserData.UserView.SignUp.class},
      message = "Ensure that the movie title included in the request are not blank")
  private String title;

  @JsonFormat(pattern = "yyyy", shape = JsonFormat.Shape.STRING)
  private Year release;

  private List<String> actors = new ArrayList<>();
  private List<String> directors = new ArrayList<>();
  private List<GenreType> genres = new ArrayList<>();
}

package br.com.ioasys.imdbservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@RequiredArgsConstructor
@Table(name = "movies")
@ToString
public class MovieModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID movieId;

  private String name;

  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "genre_id"),
      joinColumns = @JoinColumn(name = "movie_id"),
      name = "movies_genres")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToMany(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Set<GenreModel> genres;

  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "director_id"),
      joinColumns = @JoinColumn(name = "movie_id"),
      name = "movies_directors")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToMany(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Set<DirectorModel> directors;

  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "actor_id"),
      joinColumns = @JoinColumn(name = "movie_id"),
      name = "movies_actors")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToMany(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Set<ActorModel> actors;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    MovieModel that = (MovieModel) object;
    return movieId.equals(that.movieId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(movieId);
  }
}

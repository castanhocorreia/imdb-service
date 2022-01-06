package br.com.ioasys.imdbservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@RequiredArgsConstructor
@Table(name = "ratings")
@ToString
public class RatingModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID ratingId;

  @JoinColumn(name = "movie_id", nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne(optional = false)
  @ToString.Exclude
  private MovieModel movie;

  @JoinColumn(name = "user_id", nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne(optional = false)
  @ToString.Exclude
  private UserModel user;

  @Column(nullable = false)
  private int stars = 0;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    RatingModel that = (RatingModel) object;
    return ratingId.equals(that.ratingId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ratingId);
  }
}

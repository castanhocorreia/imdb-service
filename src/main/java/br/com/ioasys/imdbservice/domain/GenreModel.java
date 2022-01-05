package br.com.ioasys.imdbservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "genres")
@ToString
public class GenreModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(columnDefinition = "BINARY(36)")
  @GeneratedValue
  private UUID genreId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GenreType genre;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    GenreModel that = (GenreModel) object;
    return genreId.equals(that.genreId) && genre == that.genre;
  }

  @Override
  public int hashCode() {
    return Objects.hash(genreId, genre);
  }
}

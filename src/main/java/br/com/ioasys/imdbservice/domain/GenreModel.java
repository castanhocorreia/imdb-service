package br.com.ioasys.imdbservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "directors")
@ToString
public class GenreModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
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

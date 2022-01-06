package br.com.ioasys.imdbservice.repository;

import br.com.ioasys.imdbservice.domain.GenreModel;
import br.com.ioasys.imdbservice.domain.GenreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel, UUID> {
  Optional<GenreModel> findByGenre(GenreType genre);
}

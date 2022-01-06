package br.com.ioasys.imdbservice.repository;

import br.com.ioasys.imdbservice.domain.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, UUID> {
  Optional<PersonModel> findByFullName(String fullName);
}

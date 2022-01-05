package br.com.ioasys.imdbservice.repository;

import br.com.ioasys.imdbservice.domain.UserModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository
    extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
  boolean existsByUsername(String username);

  @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
  Optional<UserModel> findByUsername(String username);
}

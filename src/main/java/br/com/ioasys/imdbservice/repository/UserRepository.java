package br.com.ioasys.imdbservice.repository;

import br.com.ioasys.imdbservice.domain.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository
    extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
  boolean existsByUsername(String username);

  @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
  Optional<UserModel> findByUsername(String username);

  @Query(
      value =
          "SELECT u.* FROM users u JOIN users_roles ur ON u.user_id = ur.user_id JOIN roles r ON ur.role_id = r.role_id WHERE NOT r.name = 'ADMIN' AND u.status = 'ACTIVE'",
      nativeQuery = true)
  Page<UserModel> findAllActiveUsers(Specification<UserModel> spec, Pageable pageable);
}

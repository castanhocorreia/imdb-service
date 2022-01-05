package br.com.ioasys.imdbservice.repository;

import br.com.ioasys.imdbservice.domain.RoleModel;
import br.com.ioasys.imdbservice.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
  Optional<RoleModel> findByName(RoleType name);
}

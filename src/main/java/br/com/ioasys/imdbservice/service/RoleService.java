package br.com.ioasys.imdbservice.service;

import br.com.ioasys.imdbservice.domain.RoleModel;
import br.com.ioasys.imdbservice.domain.RoleType;

import java.util.Optional;

public interface RoleService {
  Optional<RoleModel> find(RoleType name);
}

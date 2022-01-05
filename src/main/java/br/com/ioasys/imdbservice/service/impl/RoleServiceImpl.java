package br.com.ioasys.imdbservice.service.impl;

import br.com.ioasys.imdbservice.domain.RoleModel;
import br.com.ioasys.imdbservice.domain.RoleType;
import br.com.ioasys.imdbservice.repository.RoleRepository;
import br.com.ioasys.imdbservice.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Optional<RoleModel> find(RoleType name) {
    return roleRepository.findByName(name);
  }
}

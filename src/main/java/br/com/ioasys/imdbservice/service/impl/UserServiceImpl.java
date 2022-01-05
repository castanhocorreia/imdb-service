package br.com.ioasys.imdbservice.service.impl;

import br.com.ioasys.imdbservice.data.UserData;
import br.com.ioasys.imdbservice.domain.RoleModel;
import br.com.ioasys.imdbservice.domain.RoleType;
import br.com.ioasys.imdbservice.domain.UserModel;
import br.com.ioasys.imdbservice.domain.UserStatus;
import br.com.ioasys.imdbservice.repository.UserRepository;
import br.com.ioasys.imdbservice.service.RoleService;
import br.com.ioasys.imdbservice.service.UserService;
import br.com.ioasys.imdbservice.util.PropertyValueRejector;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RoleService roleService;

  @Autowired
  public UserServiceImpl(
      PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.roleService = roleService;
  }

  @Override
  public UserModel create(UserData userData) {
    RoleModel roleModel =
        roleService
            .find(RoleType.USER)
            .orElseThrow(() -> new RuntimeException("ERROR: USER role is not found"));
    var userModel = new UserModel();
    BeanUtils.copyProperties(
        userData, userModel, PropertyValueRejector.rejectEmptyValues(userData));
    userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
    userModel.getRoles().add(roleModel);
    userModel.setStatus(UserStatus.ACTIVE);
    userModel.setCreatedDate(LocalDateTime.now(ZoneId.of("UTC")));
    userModel.setLastModifiedDate(userModel.getCreatedDate());
    return userRepository.save(userModel);
  }

  @Override
  public Optional<UserModel> find(UUID id) {
    return userRepository.findById(id);
  }

  @Override
  public Page<UserModel> list(Specification<UserModel> spec, Pageable pageable) {
    return userRepository.findAll(spec, pageable);
  }

  @Override
  public UserModel update(UserData userData, UserModel userModel) {
    BeanUtils.copyProperties(
        userData, userModel, PropertyValueRejector.rejectEmptyValues(userData));
    userModel.setLastModifiedDate(LocalDateTime.now(ZoneId.of("UTC")));
    return userRepository.save(userModel);
  }

  @Override
  public void delete(UserModel userModel) {
    userRepository.delete(userModel);
  }

  @Override
  public boolean checkUsernameUnavailability(String username) {
    return userRepository.existsByUsername(username);
  }
}

package br.com.ioasys.imdbservice.service;

import br.com.ioasys.imdbservice.data.UserData;
import br.com.ioasys.imdbservice.domain.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
  @Transactional
  UserModel create(UserData userData);

  Optional<UserModel> find(UUID id);

  Page<UserModel> list(Specification<UserModel> spec, Pageable pageable);

  @Transactional
  UserModel update(UserData userData, UserModel userModel);

  @Transactional
  void delete(UserModel userModel);

  boolean checkUsernameUnavailability(String username);
}

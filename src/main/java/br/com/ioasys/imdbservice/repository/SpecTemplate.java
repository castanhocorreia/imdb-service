package br.com.ioasys.imdbservice.repository;

import br.com.ioasys.imdbservice.domain.MovieModel;
import br.com.ioasys.imdbservice.domain.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecTemplate {
  public interface MovieSpec extends Specification<MovieModel> {}

  @And({@Spec(path = "fullName", spec = Like.class), @Spec(path = "username", spec = Equal.class)})
  public interface UserSpec extends Specification<UserModel> {}
}

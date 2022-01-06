package br.com.ioasys.imdbservice.service;

import br.com.ioasys.imdbservice.data.MovieData;
import br.com.ioasys.imdbservice.domain.MovieModel;
import br.com.ioasys.imdbservice.domain.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface MovieService {
  MovieModel create(MovieData movieData);

  @Transactional
  MovieModel vote(MovieModel movieModel, UserModel userModel, Integer stars);

  Optional<MovieModel> find(UUID id);

  Page<MovieModel> list(Specification<MovieModel> spec, Pageable pageable);
}

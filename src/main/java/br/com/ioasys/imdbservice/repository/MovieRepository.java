package br.com.ioasys.imdbservice.repository;

import br.com.ioasys.imdbservice.domain.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MovieRepository
    extends JpaRepository<MovieModel, UUID>, JpaSpecificationExecutor<MovieModel> {}

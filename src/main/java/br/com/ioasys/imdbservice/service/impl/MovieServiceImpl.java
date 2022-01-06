package br.com.ioasys.imdbservice.service.impl;

import br.com.ioasys.imdbservice.data.MovieData;
import br.com.ioasys.imdbservice.domain.MovieModel;
import br.com.ioasys.imdbservice.domain.PersonModel;
import br.com.ioasys.imdbservice.domain.RatingModel;
import br.com.ioasys.imdbservice.domain.UserModel;
import br.com.ioasys.imdbservice.domain.exception.UserAlreadyVotedException;
import br.com.ioasys.imdbservice.repository.GenreRepository;
import br.com.ioasys.imdbservice.repository.MovieRepository;
import br.com.ioasys.imdbservice.repository.PersonRepository;
import br.com.ioasys.imdbservice.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {
  private final GenreRepository genreRepository;
  private final MovieRepository movieRepository;
  private final PersonRepository personRepository;

  public MovieServiceImpl(
      GenreRepository genreRepository,
      MovieRepository movieRepository,
      PersonRepository personRepository) {
    this.genreRepository = genreRepository;
    this.movieRepository = movieRepository;
    this.personRepository = personRepository;
  }

  @Override
  public MovieModel create(MovieData movieData) {
    var movieModel = new MovieModel();
    movieModel.setTitle(movieData.getTitle());
    movieModel.setRelease(movieData.getRelease());
    movieData
        .getGenres()
        .forEach(
            genre ->
                genreRepository
                    .findByGenre(genre)
                    .ifPresent(genreModel -> movieModel.getGenres().add(genreModel)));
    movieData
        .getActors()
        .forEach(
            actor ->
                personRepository
                    .findByFullName(actor)
                    .ifPresentOrElse(
                        personModel -> movieModel.getActors().add(personModel),
                        () ->
                            movieModel
                                .getActors()
                                .add(personRepository.save(new PersonModel(actor)))));
    movieData
        .getDirectors()
        .forEach(
            director ->
                personRepository
                    .findByFullName(director)
                    .ifPresentOrElse(
                        personModel -> movieModel.getDirectors().add(personModel),
                        () ->
                            movieModel
                                .getDirectors()
                                .add(personRepository.save(new PersonModel(director)))));
    movieModel.setCreatedDate(LocalDateTime.now(ZoneId.of("UTC")));
    movieModel.setLastModifiedDate(movieModel.getCreatedDate());
    return movieRepository.save(movieModel);
  }

  @Override
  public MovieModel vote(MovieModel movieModel, UserModel userModel, Integer stars) {
    if (movieModel.getRatings().stream().anyMatch(rating -> rating.getUser().equals(userModel))) {
      throw new UserAlreadyVotedException(
          String.format(
              "User with id %s already voted on movie with id %s",
              userModel.getUserId(), movieModel.getMovieId()));
    }
    if (stars < 0) stars = 0;
    else if (stars > 4) stars = 4;
    var ratingModel = new RatingModel();
    ratingModel.setMovie(movieModel);
    ratingModel.setUser(userModel);
    ratingModel.setStars(stars);
    movieModel.getRatings().add(ratingModel);
    movieModel.setAverageRating(
        movieModel.getRatings().stream().mapToDouble(RatingModel::getStars).average().orElse(0.0));
    return movieRepository.save(movieModel);
  }

  @Override
  public Optional<MovieModel> find(UUID id) {
    return movieRepository.findById(id);
  }

  @Override
  public Page<MovieModel> list(Specification<MovieModel> spec, Pageable pageable) {
    return movieRepository.findAll(spec, pageable);
  }
}

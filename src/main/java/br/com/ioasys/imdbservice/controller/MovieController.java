package br.com.ioasys.imdbservice.controller;

import br.com.ioasys.imdbservice.data.MovieData;
import br.com.ioasys.imdbservice.domain.MovieModel;
import br.com.ioasys.imdbservice.domain.UserModel;
import br.com.ioasys.imdbservice.domain.exception.UserAlreadyVotedException;
import br.com.ioasys.imdbservice.repository.SpecTemplate;
import br.com.ioasys.imdbservice.service.MovieService;
import br.com.ioasys.imdbservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RestController
@RequestMapping("/movies")
public class MovieController {
  private final MovieService movieService;
  private final UserService userService;

  @Autowired
  public MovieController(MovieService movieService, UserService userService) {
    this.movieService = movieService;
    this.userService = userService;
  }

  @GetMapping()
  public ResponseEntity<Page<MovieModel>> index(
      SpecTemplate.MovieSpec spec,
      @PageableDefault(sort = "averageRating", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<MovieModel> moviePage = movieService.list(spec, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(moviePage);
  }

  @GetMapping("/alphabetical")
  public ResponseEntity<Page<MovieModel>> indexAlphabetically(
      SpecTemplate.MovieSpec spec,
      @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<MovieModel> moviePage = movieService.list(spec, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(moviePage);
  }

  @GetMapping(path = "/{movieId}")
  public ResponseEntity<Object> show(@PathVariable UUID movieId) {
    Optional<MovieModel> movieModelOptional = movieService.find(movieId);
    if (movieModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("Movie with id %s not found", movieId));
    }
    MovieModel movieModel = movieModelOptional.get();
    return ResponseEntity.status(HttpStatus.OK).body(movieModel);
  }

  @PostMapping()
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Object> store(
      Authentication authentication, @RequestBody @Validated MovieData movieData) {
    log.info("New POST request to create movie with the following data: {}", movieData.toString());
    UserDetails userDetails = (UserModel) authentication.getPrincipal();
    log.info("Authentication {}", userDetails.getUsername());
    MovieModel movieModel = movieService.create(movieData);
    log.info("New movie created successfully with id {}", movieData.getMovieId());
    return ResponseEntity.status(HttpStatus.CREATED).body(movieModel);
  }

  @PostMapping("/{movieId}/vote/{stars}")
  @PreAuthorize("hasAnyRole('USER')")
  public ResponseEntity<Object> vote(
      @PathVariable @Valid UUID movieId,
      @Valid @PathVariable @Min(0) @Max(4) Integer stars,
      Authentication authentication) {
    log.info("New POST request to vote in movie with id {}", movieId);
    UserDetails userDetails = (UserModel) authentication.getPrincipal();
    log.info("Authentication {}", userDetails.getUsername());
    Optional<MovieModel> movieModelOptional = movieService.find(movieId);
    Optional<UserModel> userModelOptional =
        userService.find(((UserDetails) authentication.getPrincipal()).getUsername());
    if (movieModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("Movie with id %s not found", movieId));
    }
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    try {
      MovieModel movieModel =
          movieService.vote(movieModelOptional.get(), userModelOptional.get(), stars);
      return ResponseEntity.status(HttpStatus.CREATED).body(movieModel);
    } catch (UserAlreadyVotedException userAlreadyVotedException) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(userAlreadyVotedException.getMessage());
    }
  }
}

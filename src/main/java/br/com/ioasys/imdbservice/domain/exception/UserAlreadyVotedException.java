package br.com.ioasys.imdbservice.domain.exception;

public class UserAlreadyVotedException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UserAlreadyVotedException(String message) {
    super(message);
  }
}

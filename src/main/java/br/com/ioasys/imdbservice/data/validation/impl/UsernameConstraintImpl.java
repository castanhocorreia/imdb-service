package br.com.ioasys.imdbservice.data.validation.impl;

import br.com.ioasys.imdbservice.data.validation.UsernameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {
  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    if (username == null) return true;
    return (!username.contains(" ") && username.length() >= 3);
  }
}

package br.com.ioasys.imdbservice.data.validation;

import br.com.ioasys.imdbservice.data.validation.impl.UsernameConstraintImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UsernameConstraintImpl.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface UsernameConstraint {
  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String message() default "Invalid username!";
}

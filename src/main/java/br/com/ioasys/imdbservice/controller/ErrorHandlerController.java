package br.com.ioasys.imdbservice.controller;

import br.com.ioasys.imdbservice.data.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RestController
public class ErrorHandlerController {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorData> handleException(MethodArgumentNotValidException exception) {
    List<FieldError> errors = exception.getBindingResult().getFieldErrors();
    List<ErrorData.ErrorDetails> errorDetails = new ArrayList<>();
    for (FieldError fieldError : errors) {
      ErrorData.ErrorDetails error = new ErrorData.ErrorDetails();
      error.setFieldName(fieldError.getField());
      error.setMessage(fieldError.getDefaultMessage());
      errorDetails.add(error);
    }
    ErrorData errorData = new ErrorData();
    errorData.setErrors(errorDetails);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorData);
  }
}

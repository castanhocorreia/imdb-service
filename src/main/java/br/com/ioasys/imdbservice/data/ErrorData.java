package br.com.ioasys.imdbservice.data;

import lombok.Data;

import java.util.List;

@Data
public class ErrorData {
  private List<ErrorDetails> errors;

  @Data
  public static class ErrorDetails {
    private String fieldName;
    private String message;
  }
}

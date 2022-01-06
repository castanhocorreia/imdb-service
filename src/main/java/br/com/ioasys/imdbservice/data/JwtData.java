package br.com.ioasys.imdbservice.data;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtData {
  @NonNull private String token;
  private String type = "Bearer";
}

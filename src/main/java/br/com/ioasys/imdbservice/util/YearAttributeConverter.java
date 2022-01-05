package br.com.ioasys.imdbservice.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter
public class YearAttributeConverter implements AttributeConverter<Year, Short> {
  @Override
  public Short convertToDatabaseColumn(Year attribute) {
    if (attribute != null) {
      return (short) attribute.getValue();
    }
    return null;
  }

  @Override
  public Year convertToEntityAttribute(Short data) {
    if (data != null) {
      return Year.of(data);
    }
    return null;
  }
}

package de.freitag.stefan.spring.sht21.server.api.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SensorUtils {

  public static boolean isValidUuid(final String text) {
    if (text == null) {
      return false;
    }
    return text.matches(
        "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
  }
}

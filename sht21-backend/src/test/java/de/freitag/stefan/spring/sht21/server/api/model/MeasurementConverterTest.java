package de.freitag.stefan.spring.sht21.server.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class MeasurementConverterTest {

  MeasurementConverter converter = new MeasurementConverter();

  @Test
  void convert() {
    Measurement measurement =
        Measurement.builder()
            .value(22.5)
            .unit("Celsius")
            .measuredAt(Instant.ofEpochMilli(1577566800000L))
            .build();
    MeasurementDTO dto = converter.convert(measurement);
    assertTrue(measurement.getUnit().contentEquals(dto.getUnit()));
    assertEquals(measurement.getMeasuredAt(), Instant.ofEpochMilli(dto.getMeasuredAt()));
    assertEquals(measurement.getValue(), dto.getValue().doubleValue());
  }
}

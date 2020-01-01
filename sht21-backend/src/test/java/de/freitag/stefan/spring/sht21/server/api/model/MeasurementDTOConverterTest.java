package de.freitag.stefan.spring.sht21.server.api.model;

import static org.junit.jupiter.api.Assertions.*;

import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MeasurementDTOConverterTest {

  @Test
  void convert() {
    MeasurementDTOConverter converter = new MeasurementDTOConverter();
    MeasurementDTO dto =
        MeasurementDTO.builder()
            .value(BigDecimal.valueOf(22.5))
            .unit("Celsius")
            .measuredAt(1577566800000L)
            .build();
    Measurement measurement = converter.convert(dto);
    assertTrue(dto.getUnit().contentEquals(measurement.getUnit()));
    assertEquals(0, dto.getValue().compareTo(BigDecimal.valueOf(measurement.getValue())));
  }
}

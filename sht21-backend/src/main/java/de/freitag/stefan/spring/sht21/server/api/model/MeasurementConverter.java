package de.freitag.stefan.spring.sht21.server.api.model;

import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import lombok.NonNull;
import org.modelmapper.AbstractConverter;

public class MeasurementConverter extends AbstractConverter<Measurement, MeasurementDTO> {
  @Override
  protected MeasurementDTO convert(@NonNull final Measurement measurement) {
    return measurement == null ? null : MeasurementDTO.fromMeasurement(measurement);
  }
}

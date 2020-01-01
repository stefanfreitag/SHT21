package de.freitag.stefan.spring.sht21.server.api.model;

import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import lombok.NonNull;
import org.modelmapper.AbstractConverter;

public class MeasurementDTOConverter extends AbstractConverter<MeasurementDTO, Measurement> {
  @Override
  protected Measurement convert(@NonNull final MeasurementDTO source) {
    return source == null ? null : Measurement.fromDTO(source);
  }
}

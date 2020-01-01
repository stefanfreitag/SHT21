package de.freitag.stefan.spring.sht21.server.domain.model;

import de.freitag.stefan.spring.sht21.server.api.model.MeasurementDTO;
import java.io.Serializable;
import java.time.Instant;
import lombok.*;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.TimeColumn;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@org.influxdb.annotation.Measurement(name = "sht_21_measurements")
public class Measurement implements Serializable {

  @Column(name = "uuid", tag = true)
  private String uuid;

  @TimeColumn
  @Column(name = "time")
  private Instant measuredAt;

  @Column(name = "value")
  private double value;

  @Column(name = "unit")
  private String unit;

  @Column(name = "type")
  private String type;

  public static Measurement fromDTO(@NonNull final MeasurementDTO measurementDTO) {
    return Measurement.builder()
        .value(measurementDTO.getValue().doubleValue())
        .unit(measurementDTO.getUnit())
        .measuredAt(Instant.ofEpochMilli(measurementDTO.getMeasuredAt()))
        .build();
  }
}

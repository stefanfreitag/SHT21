package de.freitag.stefan.spring.sht21.server.api.model;

import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class MeasurementDTO {
  @Schema(description = "Measurement unit.")
  private String unit;

  @Schema(description = "Measured value.")
  private BigDecimal value;

  @Schema(description = "Timestamp of measurement in milliseconds since epoch.")
  private long measuredAt;

  public static MeasurementDTO fromMeasurement(@NonNull final Measurement source) {
    return MeasurementDTO.builder()
        .value(BigDecimal.valueOf(source.getValue()))
        .unit(source.getUnit())
        .measuredAt(source.getMeasuredAt().toEpochMilli())
        .build();
  }
}

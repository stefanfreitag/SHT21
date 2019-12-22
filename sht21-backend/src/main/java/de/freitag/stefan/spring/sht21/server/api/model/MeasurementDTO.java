package de.freitag.stefan.spring.sht21.server.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MeasurementDTO {
  @Schema(description = "Measurement unit.")
  private String unit;

  @Schema(description = "Measured value.")
  private BigDecimal value;

  @Schema(description = "Timestamp of measurement in milliseconds since epoch.")
  private long measuredAt;
}

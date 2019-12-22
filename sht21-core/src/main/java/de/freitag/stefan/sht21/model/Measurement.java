package de.freitag.stefan.sht21.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

/**
 * A {@code Measurement} consists of
 *
 * <ul>
 *   <li>The measured value
 *   <li>The unit of the measured value
 *   <li>The creation date and time in UTC
 * </ul>
 */
@Builder
@Value
@Getter
public class Measurement {
  /** Measurement was taken at this timestamp. The timezone is UTC. */
  private Long measuredAt;
  /** The measured value. */
  private BigDecimal value;
  /** The base unit. */
  private String unit;
}

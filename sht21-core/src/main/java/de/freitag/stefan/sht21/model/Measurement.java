package de.freitag.stefan.sht21.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

/**
 * A {@code Measurement} consists of
 * <ul>
 * <li>The measured value</li>
 * <li>The unit of the measured value</li>
 * <li>The creation date and time in UTC</li>
 * </ul>
 */
@Builder
@Value
@Getter
public class Measurement {
    /**
     * Measurement was taken at this timestamp. The timezone is UTC.
     */
    private Long measuredAt;
    /**
     * The measured value.
     */
    private BigDecimal value;
    /**
     * The base unit.
     */
    private String unit;
}

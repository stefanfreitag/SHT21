package de.freitag.stefan.sht21.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * A {@code Measurement} consists of
 * <ul>
 * <li>The measured value</li>
 * <li>The creation date and time in UTC</li>
 * </ul>
 */
@Builder
@Value
@Getter
public class Measurement {
    @Builder.Default
    private final LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("UTC"));
    private final BigDecimal value;
    private final String unit;
}

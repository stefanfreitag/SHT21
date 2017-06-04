package de.freitag.stefan.sht21.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.measure.Quantity;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * A {@code Measurement} consists of
 * <ul>
 * <li>The measured value as {@link Quantity}</li>
 * <li>The creation date and time in UTC</li>
 * </ul>
 */
@Value
@Getter
@Builder
public class Measurement {
    @Builder.Default
    private final LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("UTC"));
    private final Quantity<?> value;
}

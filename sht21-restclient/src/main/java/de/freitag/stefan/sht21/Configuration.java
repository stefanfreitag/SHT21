package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Configuration {
    /**
     * Unique identifier for the sensor.
     */
    private UUID uuid;

    private String endpoint;
    /**
     * Measurement interval in seconds.
     */
    private long interval;

    private MeasureType measureType;

    private SensorType implementation;


}

package de.freitag.stefan.sht21.rest;

import io.dropwizard.Configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Application configuration for SHT21 REST.
 *
 * @author Stefan Freitag
 */
final class SHT21RestConfiguration extends Configuration {

    /**
     * Start-up delay of measurement task in seconds.
     */
    @Min(0)
    @Max(3600)
    @NotNull
    private Integer startDelay;

    /**
     * Interval in seconds between taking measurements.
     */
    @Min(5)
    @Max(3600)
    @NotNull
    private Integer interval;

    /**
     * The I2C bus to use. Default is 1.
     */
    @Min(0)
    @Max(1)
    @NotNull
    private Integer i2cBus;

    /**
     * The device address on the bus. Default is 0x40.
     */
    @Min(0)
    @Max(255)
    @NotNull
    private Integer i2cAddress;

    /**
     * Return the start delay of the {@link MeasurementTask} in seconds.
     *
     * @return Start delay of the {@link MeasurementTask} in seconds.
     */
    public Integer getStartDelay() {
        return startDelay;
    }

    public Integer getInterval() {
        return interval;
    }

    /**
     * Return the I2C bus to use.
     *
     * @return the I2C bus to use.
     */
    public Integer getI2cBus() {
        return i2cBus;
    }

    /**
     * Return the device address on the bus.
     *
     * @return the device address on the bus.
     */
    public Integer getI2cAddress() {
        return i2cAddress;
    }
}


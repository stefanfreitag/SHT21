package de.freitag.stefan.sht21.rest;


import de.freitag.stefan.sht21.model.*;

import java.util.Date;

/**
 * Contains a complete set of measurements and additional information:
 * <ul>
 * <li>Creation date of the object.</li>
 * <li>Heater status</li>
 * <li>End of battery info</li>
 * <li>Measurement resolution</li>
 * <li>Temperature measurement</li>
 * <li>Humidity measurement</li>
 * </ul>
 *
 * @author Stefan Freitag
 */
final class SHT21Status {

    /**
     * Date this {@link de.freitag.stefan.sht21.rest.SHT21Status} was created at.
     */
    private final Date createdAt;
    private final HeaterStatus heaterStatus;
    private final EndOfBatteryAlert eobStatus;
    private final Measurement temperature;
    private final Measurement humidity;
    private final Resolution resolution;

    /**
     * Create a new {@link de.freitag.stefan.sht21.rest.SHT21Status}.
     *
     * @param heaterStatus A {@link HeaterStatus}.
     * @param eobStatus    An {@link EndOfBatteryAlert}.
     * @param temperature  A temperature {@link Measurement}.
     * @param humidity     A humidity {@link Measurement}.
     * @param resolution   The measurement {@link Resolution}.
     */
    private SHT21Status(final HeaterStatus heaterStatus, final EndOfBatteryAlert eobStatus, final Measurement temperature, final Measurement humidity, final Resolution resolution) {
        this.createdAt = new Date();
        this.heaterStatus = heaterStatus;
        this.eobStatus = eobStatus;
        this.temperature = temperature;
        this.humidity = humidity;
        this.resolution = resolution;
    }

    /**
     * Create a new {@link de.freitag.stefan.sht21.rest.SHT21Status}.
     *
     * @param heaterStatus A {@link HeaterStatus}.
     * @param eobStatus    An {@link EndOfBatteryAlert}.
     * @param temperature  A temperature {@link Measurement}.
     * @param humidity     A humidity {@link Measurement}.
     * @param resolution   The measurement {@link Resolution}.
     * @return A new {@link SHT21Status}.
     */
    public static SHT21Status create(final HeaterStatus heaterStatus, final EndOfBatteryAlert eobStatus, final Measurement temperature, final Measurement humidity, final Resolution resolution) {
        if (heaterStatus == null) {
            throw new IllegalArgumentException(HeaterStatus.class.getSimpleName() + " is null");
        }

        if (eobStatus == null) {
            throw new IllegalArgumentException(EndOfBatteryAlert.class.getSimpleName() + " is null");
        }

        if (temperature == null) {
            throw new IllegalArgumentException("Temperature measurement is null");
        }

        if (temperature.getType() != MeasureType.TEMPERATURE) {
            throw new IllegalArgumentException("Temperature measurement has wrong type. Found: " + temperature.getType());
        }

        if (humidity == null) {
            throw new IllegalArgumentException("Humidity measurement is null");
        }

        if (humidity.getType() != MeasureType.HUMIDITY) {
            throw new IllegalArgumentException("Humidity measurement has wrong type. Found: " + temperature.getType());
        }
        if (resolution == null) {
            throw new IllegalArgumentException(Resolution.class.getSimpleName() + " is null");
        }
        return new SHT21Status(heaterStatus, eobStatus, temperature, humidity, resolution);
    }

    /**
     * Return the {@link java.util.Date} this {@link de.freitag.stefan.sht21.rest.SHT21Status} was created at.
     *
     * @return The {@link java.util.Date} this {@link de.freitag.stefan.sht21.rest.SHT21Status} was created at.
     */
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Return the {@link de.freitag.stefan.sht21.model.HeaterStatus}.
     *
     * @return The {@link de.freitag.stefan.sht21.model.HeaterStatus}.
     */
    public HeaterStatus getHeaterStatus() {
        return this.heaterStatus;
    }

    /**
     * Return the {@link de.freitag.stefan.sht21.model.EndOfBatteryAlert}.
     *
     * @return The {@link de.freitag.stefan.sht21.model.EndOfBatteryAlert}.
     */

    public EndOfBatteryAlert getEobStatus() {
        return this.eobStatus;
    }

    /**
     * Return the temperature {@link de.freitag.stefan.sht21.model.Measurement}.
     *
     * @return The temperature {@link de.freitag.stefan.sht21.model.Measurement}.
     */
    public Measurement getTemperature() {
        return this.temperature;
    }

    /**
     * Return the humidity {@link de.freitag.stefan.sht21.model.Measurement}.
     *
     * @return The humidity {@link de.freitag.stefan.sht21.model.Measurement}.
     */
    public Measurement getHumidity() {
        return this.humidity;
    }

    /**
     * Return the {@link de.freitag.stefan.sht21.model.Resolution}.
     *
     * @return The {@link de.freitag.stefan.sht21.model.Resolution}.
     */
    public Resolution getResolution() {
        return this.resolution;
    }

    @Override
    public String toString() {
        return "SHT21Status{" +
                "createdAt=" + createdAt +
                ", heaterStatus=" + heaterStatus +
                ", eobStatus=" + eobStatus +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", resolution=" + resolution +
                '}';
    }

}

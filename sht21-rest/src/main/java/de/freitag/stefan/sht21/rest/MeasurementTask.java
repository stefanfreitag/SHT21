package de.freitag.stefan.sht21.rest;

import de.freitag.stefan.sht21.SHT21;
import de.freitag.stefan.sht21.SHT21Impl;
import de.freitag.stefan.sht21.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Task that periodically executes the temperature and
 * humidity measurements.
 *
 * @author Stefan Freitag
 */
final class MeasurementTask {

    /**
     * Default start delay in seconds.
     */
    private static final int DEFAULT_DELAY = 5;
    /**
     * Default measurement interval in seconds.
     */
    private static final int DEFAULT_INTERVAL = 30;

    private static final Logger LOG = LogManager.getLogger(MeasurementTask.class.getCanonicalName());
    /**
     * The {@link SHT21} implementation.
     */
    private final SHT21 sht21;
    /**
     * Start delay in seconds.
     */
    private final int delay;
    /**
     * Measurement interval in seconds.
     */
    private final int interval;
    /**
     * The latest {@link SHT21Status}.
     */
    private SHT21Status latestStatus;

    /**
     * Create a new {@link MeasurementTask}.
     *
     * @param delay    The start delay in seconds.
     * @param interval The measurement interval in seconds.
     */
    private MeasurementTask(final SHT21 sht21, final int delay, final int interval) {
        this.sht21 = sht21;
        this.delay = delay;
        this.interval = interval;
        this.initializeAndStart();
    }

    /**
     * Create a {@link MeasurementTask} with default
     * start delay and interval.
     *
     * @return A new {@link MeasurementTask}.
     */
    public static MeasurementTask create() {
        return new MeasurementTask(SHT21Impl.create(1, 0x40), DEFAULT_DELAY, DEFAULT_INTERVAL);

    }

    /**
     * Create a {@link MeasurementTask}.
     *
     * @param startDelay The start delay in seconds.
     * @param interval   The measurement interval in seconds.
     * @return A new {@link MeasurementTask}.
     * @throws IllegalArgumentException if
     *                                  <ul>
     *                                  <li>{@code startDelay} is less than zero seconds</li>
     *                                  <li>{@code intertval} is less than 10 seconds</li>
     *                                  </ul>
     */
    public static MeasurementTask create(final SHT21 sht21, final int startDelay, final int interval) {
        if (startDelay < 0) {
            throw new IllegalArgumentException("Start delay must be greater than or equal to zero. Value: " + startDelay);
        }
        if (interval < 10) {
            throw new IllegalArgumentException("Minimum interval is 10 seconds. Value: " + interval);
        }
        return new MeasurementTask(sht21, startDelay, interval);
    }


    /**
     * Return the {@link org.apache.logging.log4j.Logger} for this class.
     *
     * @return The {@link org.apache.logging.log4j.Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(MeasurementTask.class.getCanonicalName());
    }

    private void initializeAndStart() {

        final TimerTask action = new TimerTask() {
            @Override
            public void run() {
                getLogger().info("Starting measurements");
                final HeaterStatus heaterStatus = sht21.getHeaterStatus();
                final EndOfBatteryAlert endOfBatteryAlert = sht21.getEndOfBatteryAlert();

                try {
                    final Measurement measurement = sht21.measurePoll(MeasureType.TEMPERATURE);
                    final Measurement measurement1 = sht21.measurePoll(MeasureType.HUMIDITY);
                    final Resolution resolution = sht21.getResolution();
                    MeasurementTask.this.latestStatus = SHT21Status.create(heaterStatus, endOfBatteryAlert, measurement, measurement1, resolution);
                } catch (final UnsupportedMeasureTypeException exception) {
                    LOG.error(exception.getMessage(), exception);
                }

            }
        };

        final Timer caretaker = new Timer();
        caretaker.schedule(action, TimeUnit.SECONDS.toMillis(this.delay), TimeUnit.SECONDS.toMillis(this.interval));
    }

    /**
     * Return the {@link java.util.Date} this {@link de.freitag.stefan.sht21.rest.SHT21Status} was created at.
     *
     * @return The {@link java.util.Date} this {@link de.freitag.stefan.sht21.rest.SHT21Status} was created at.
     * @throws IllegalStateException if task did not execute until now.
     */
    public Date getCreatedAt() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No timestamp information available.");
        }
        return this.latestStatus.getCreatedAt();
    }

    /**
     * Return the {@link de.freitag.stefan.sht21.model.HeaterStatus}.
     *
     * @return The {@link de.freitag.stefan.sht21.model.HeaterStatus}.
     * @throws IllegalStateException if task did not execute until now.
     */
    public HeaterStatus getHeaterStatus() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No heater information available.");
        }
        return this.latestStatus.getHeaterStatus();
    }

    /**
     * Return the {@link de.freitag.stefan.sht21.model.EndOfBatteryAlert}.
     *
     * @return The {@link de.freitag.stefan.sht21.model.EndOfBatteryAlert}.
     * @throws IllegalStateException if task did not execute until now.
     */

    public EndOfBatteryAlert getEobStatus() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No end-of-battery information available.");
        }
        return this.latestStatus.getEobStatus();
    }

    /**
     * Return the temperature {@link de.freitag.stefan.sht21.model.Measurement}.
     *
     * @return The temperature {@link de.freitag.stefan.sht21.model.Measurement}.
     * @throws IllegalStateException if task did not execute until now.
     */
    public Measurement getTemperature() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No temperature information available.");
        }
        return this.latestStatus.getTemperature();
    }

    /**
     * Return the humidity {@link de.freitag.stefan.sht21.model.Measurement}.
     *
     * @return The humidity {@link de.freitag.stefan.sht21.model.Measurement}.
     * @throws IllegalStateException if task did not execute until now.
     */
    public Measurement getHumidity() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No humidity information available.");
        }
        return this.latestStatus.getHumidity();
    }

    /**
     * Return the {@link de.freitag.stefan.sht21.model.Resolution}.
     *
     * @return The {@link de.freitag.stefan.sht21.model.Resolution}.
     * @throws IllegalStateException if task did not execute until now.
     */
    public Resolution getResolution() {
        if (this.latestStatus == null) {
            throw new IllegalStateException("Task did not execute until now. No resolution information available.");
        }
        return this.latestStatus.getResolution();
    }


}

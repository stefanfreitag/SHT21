package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import de.freitag.stefan.sht21.model.UnsupportedMeasureTypeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A {@link MeasurementTask} contains information about the type of measurement
 * (temperature/ humidity) to make and the desired interval between two measurements.
 */
public final class MeasurementTask {

    /**
     * The minimum interval between measurements.
     */
    private static final long MINIMUM_INTERVAL = 5000L;
    /**
     * Device address.
     */
    private static final int I2C_ADDRESS = 0x40;
    /**
     * The unique identifier of this task.
     */
    private final UUID uuid;
    /**
     * The interval between measurements.
     */
    private final long interval;
    private ScheduledExecutorService service;
    private List<MeasurementTaskListener> listeners;
    private SHT21 sht21;


    /**
     * Create a new {@link MeasurementTask}
     *
     * @param interval    The interval between two measurements.
     * @param measureType A non-null {@link MeasureType}.
     */
    public MeasurementTask(final long interval, final MeasureType measureType) {
        if (measureType == null) {
            throw new IllegalArgumentException(MeasureType.class.getSimpleName() + " is null");
        }
        if (interval < MINIMUM_INTERVAL) {
            throw new IllegalArgumentException("Interval must be greater than " + MINIMUM_INTERVAL + " milliseconds.");
        }
        this.uuid = UUID.randomUUID();
        this.listeners = new CopyOnWriteArrayList<>();
        this.interval = interval;
        this.service = Executors.newScheduledThreadPool(1);
        this.service.scheduleWithFixedDelay(() -> {

            try {
                final Measurement measurement = sht21.measurePoll(measureType);
                this.fireReceivedMeasurement(measurement);

            } catch (final UnsupportedMeasureTypeException exception) {
                getLogger().error(exception.getMessage(), exception);
            }
        }, 1_000L, this.interval, TimeUnit.MILLISECONDS);

        this.sht21 = new SHT21DummyImpl();
//        this.sht21 = SHT21Impl.create(I2CBus.BUS_1, I2C_ADDRESS);

    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(MeasurementTask.class.getCanonicalName());
    }

    /**
     * Return the unique id of this task.
     *
     * @return {@link UUID} of this task.
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * Return the desired interval between to measurements for this task.
     *
     * @return Desired interval in milliseconds.
     */
    public long getInterval() {
        return this.interval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementTask that = (MeasurementTask) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    @Override
    public String toString() {
        return "MeasurementTask{" +
                "interval=" + interval +
                ", uuid=" + uuid +
                '}';
    }

    public boolean addListener(MeasurementTaskListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        if (this.listeners.contains(listener)) {
            return false;
        }
        return this.listeners.add(listener);
    }

    private void fireReceivedMeasurement(final Measurement measurement) {
        assert measurement != null;
        for (final MeasurementTaskListener listener : this.listeners) {
            listener.onReceived(measurement);
        }
    }
}

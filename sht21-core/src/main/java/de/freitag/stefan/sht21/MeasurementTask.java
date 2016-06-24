package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;

import java.util.Objects;
import java.util.UUID;

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
     * The unique identifier of this task.
     */
    private final UUID uuid;
    /**
     * The interval between measurements.
     */
    private final long interval;


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
        this.interval = interval;
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
}

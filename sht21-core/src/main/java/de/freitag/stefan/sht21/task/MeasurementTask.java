package de.freitag.stefan.sht21.task;

import de.freitag.stefan.sht21.SHT21;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import de.freitag.stefan.sht21.model.UnsupportedMeasureTypeException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import lombok.*;
import lombok.extern.log4j.Log4j2;

/**
 * A {@link MeasurementTask} contains information about the type of measurement (temperature/
 * humidity) to make and the desired interval between two measurements.
 */
@Value
@Builder
@Log4j2
@EqualsAndHashCode(callSuper = true)
@ToString
public final class MeasurementTask extends AbstractTask {

  /** The minimum interval between measurements. */
  private static final long MINIMUM_INTERVAL = 5000L;
  /** Device address. */
  private static final int I2C_ADDRESS = 0x40;

  /** The interval between two measurements. */
  private final long interval;

  private MeasureType measureType;
  // TODO: Remove from builder
  @Builder.Default
  private final List<MeasurementTaskListener> listeners = new CopyOnWriteArrayList<>();

  private SHT21 sht21;

  @Override
  public void start() {
    super.start();
    this.getService()
        .scheduleWithFixedDelay(
            () -> {
              try {
                final Measurement measurement = sht21.measurePoll(measureType);
                this.fireReceivedMeasurement(measurement);

              } catch (final UnsupportedMeasureTypeException exception) {
                log.error(exception.getMessage(), exception);
              }
            },
            1_000L,
            this.interval,
            TimeUnit.MILLISECONDS);
  }

  /**
   * Register a {@link MeasurementTaskListener} for receiving updates.
   *
   * @param listener A non-null {@link MeasurementTaskListener}.
   * @return {@code true} if the listener was added. Otherwise {@code false} is returned.
   */
  public boolean addListener(@NonNull final MeasurementTaskListener listener) {
    return !this.listeners.contains(listener) && this.listeners.add(listener);
  }

  /**
   * Notify registered listeners about a {@link Measurement}.
   *
   * @param measurement A non-null {@link Measurement}.
   */
  private void fireReceivedMeasurement(@NonNull final Measurement measurement) {
    this.listeners.forEach(listener -> listener.onReceived(measurement));
  }
}

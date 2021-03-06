package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.EndOfBatteryAlert;
import de.freitag.stefan.sht21.model.HeaterStatus;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import de.freitag.stefan.sht21.model.Resolution;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Random;
import lombok.NonNull;

/** A dummy implementation of the SHT21 interface. */
public final class SHT21DummyImpl extends AbstractSHT21 {

  /** Used to generate random measurement values. */
  private static final Random RAND = new Random();

  /**
   * Return the present resolution (in bits) for temperature and humidity measurement.
   *
   * @return Present {@link de.freitag.stefan.sht21.model.Resolution}.
   */
  @Override
  public Resolution getResolution() {
    final int i = RAND.nextInt(Integer.MAX_VALUE) % Resolution.values().length;
    return Resolution.values()[i];
  }

  /**
   * Return the status of the {@link de.freitag.stefan.sht21.model.EndOfBatteryAlert}.
   *
   * @return Present {@link de.freitag.stefan.sht21.model.EndOfBatteryAlert} status.
   */
  @Override
  public EndOfBatteryAlert getEndOfBatteryAlert() {
    if (RAND.nextInt() % 2 == 0) {
      return EndOfBatteryAlert.EOB_ALERT_OFF;
    }
    return EndOfBatteryAlert.EOB_ALERT_ON;
  }

  /**
   * Return the {@link de.freitag.stefan.sht21.model.HeaterStatus}.
   *
   * @return Present {@link de.freitag.stefan.sht21.model.HeaterStatus}.
   */
  @Override
  public HeaterStatus getHeaterStatus() {
    if (RAND.nextInt() % 2 == 0) {
      return HeaterStatus.HEATER_OFF;
    }
    return HeaterStatus.HEATER_ON;
  }

  /**
   * Measures humidity or temperature.
   *
   * @param measureType Either temperature or humidity measurement.
   * @return A {@link de.freitag.stefan.sht21.model.Measurement}.
   */
  @Override
  public Measurement measurePoll(@NonNull final MeasureType measureType) {
    if (MeasureType.HUMIDITY.equals(measureType)) {
      return Measurement.builder()
          .measuredAt(Instant.now().toEpochMilli())
          .value(BigDecimal.valueOf(Math.abs(RAND.nextFloat())).setScale(2, RoundingMode.HALF_EVEN))
          .unit("%RH")
          .build();
    } else if (MeasureType.TEMPERATURE.equals(measureType)) {
      return Measurement.builder()
          .measuredAt(Instant.now().toEpochMilli())
          .value(BigDecimal.valueOf(Math.abs(RAND.nextFloat())).setScale(2, RoundingMode.HALF_EVEN))
          .unit("CELSIUS")
          .build();
    } else {
      throw new IllegalArgumentException("Unsupported Measurement type: " + measureType);
    }
  }
}

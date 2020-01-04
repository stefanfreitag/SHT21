package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.EndOfBatteryAlert;
import de.freitag.stefan.sht21.model.HeaterStatus;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import de.freitag.stefan.sht21.model.Resolution;
import de.freitag.stefan.sht21.model.UnsupportedMeasureTypeException;

public interface SHT21 {

  /**
   * Return the present resolution (in bits) for temperature and humidity measurement.
   *
   * @return Present {@link Resolution}.
   */
  Resolution getResolution();

  /**
   * Return the status of the {@link EndOfBatteryAlert}.
   *
   * @return Present {@link EndOfBatteryAlert} status.
   */
  EndOfBatteryAlert getEndOfBatteryAlert();

  /**
   * Return the {@link HeaterStatus}.
   *
   * @return Present {@link HeaterStatus}.
   */
  HeaterStatus getHeaterStatus();

  /**
   * Measures humidity or temperature.
   *
   * @param measureType Either temperature or humidity measurement.
   * @return A {@link Measurement}.
   * @throws UnsupportedMeasureTypeException if an unsupported measure type was specified.
   */
  Measurement measurePoll(MeasureType measureType) throws UnsupportedMeasureTypeException;
}

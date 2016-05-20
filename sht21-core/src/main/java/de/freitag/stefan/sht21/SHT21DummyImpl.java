package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.*;

import java.util.Random;

/**
 * A dummy implementation of the SHT21 interface.
 */
public final class SHT21DummyImpl implements SHT21 {

    private static final Random RAND = new Random();


    /**
     * Return the present resolution (in bits) for temperature and humidity measurement.
     *
     * @return Present  {@link de.freitag.stefan.sht21.model.Resolution}.
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
    public Measurement measurePoll(final MeasureType measureType) {
        if (measureType == null) {
            throw new IllegalArgumentException(MeasureType.class.getSimpleName() + " is null");
        }
        return Measurement.create(Math.abs(RAND.nextFloat()), measureType);
    }
}
package de.freitag.stefan.sht21.rest;

import de.freitag.stefan.sht21.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link SHT21Status}.
 *
 * @author Stefan Freitag
 */
public final class SHT21StatusTest {

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullHeaterThrowsIllegalArgumentException() {
        SHT21Status.create(
                null, EndOfBatteryAlert.EOB_ALERT_ON, Measurement.create(1, MeasureType.TEMPERATURE), Measurement.create(1, MeasureType.HUMIDITY), Resolution.RES_11_11BIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullEndOfBatteryThrowsIllegalArgumentException() {
        SHT21Status.create(
                HeaterStatus.HEATER_ON, null, Measurement.create(1, MeasureType.TEMPERATURE), Measurement.create(1, MeasureType.HUMIDITY), Resolution.RES_11_11BIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullTemperatureMeasurementThrowsIllegalArgumentException() {
        SHT21Status.create(
                HeaterStatus.HEATER_ON, EndOfBatteryAlert.EOB_ALERT_ON, null, Measurement.create(1, MeasureType.HUMIDITY), Resolution.RES_11_11BIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullHumidityMeasurementThrowsIllegalArgumentException() {
        SHT21Status.create(
                HeaterStatus.HEATER_ON, EndOfBatteryAlert.EOB_ALERT_ON, Measurement.create(1, MeasureType.TEMPERATURE), null, Resolution.RES_11_11BIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullResolutionThrowsIllegalArgumentException() {
        SHT21Status.create(
                HeaterStatus.HEATER_ON, EndOfBatteryAlert.EOB_ALERT_ON, Measurement.create(1, MeasureType.TEMPERATURE), Measurement.create(1, MeasureType.HUMIDITY), null);
    }

    @Test
    public void createReturnsObjectWithExpectedValues() {
        final SHT21Status status = SHT21Status.create(
                HeaterStatus.HEATER_ON, EndOfBatteryAlert.EOB_ALERT_ON, Measurement.create(1, MeasureType.TEMPERATURE), Measurement.create(2, MeasureType.HUMIDITY), Resolution.RES_10_13BIT);
        assertEquals(HeaterStatus.HEATER_ON, status.getHeaterStatus());
        assertEquals(EndOfBatteryAlert.EOB_ALERT_ON, status.getEobStatus());
        assertEquals(Resolution.RES_10_13BIT, status.getResolution());
        assertEquals(0, Float.compare(1, status.getTemperature().getValue()));
        assertEquals(0, Float.compare(2, status.getHumidity().getValue()));
    }
}

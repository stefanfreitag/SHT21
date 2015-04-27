package de.freitag.stefan.sht21.model;

import org.junit.Test;

import static org.junit.Assert.*;

public final class MeasurementTest {

    @Test(expected = NullPointerException.class)
    public void createWithNullTypeThrowsNullPointerException() {
        Measurement.create(0.0f, null);
    }

    @Test
    public void createReturnsExpectedObject() {
        Measurement measurement = Measurement.create(12.2f, MeasureType.HUMIDITY);
        assertEquals(12.2f, measurement.getValue(), 0.00001f);
        assertEquals(MeasureType.HUMIDITY, measurement.getType());
    }

    @Test
    public void equalsWithAnotherMeasurementReturnsFalse() {
        Measurement measurement1 = Measurement.create(12.2f, MeasureType.HUMIDITY);
        Measurement measurement2 = Measurement.create(12.2f, MeasureType.TEMPERATURE);
        assertTrue(measurement1.hashCode() != measurement2.hashCode());
        assertFalse(measurement1.equals(measurement2));
        assertFalse(measurement2.equals(measurement1));
    }

    @Test
    public void equalsWithSameMeasurementReturnsTrue() {
        Measurement measurement1 = Measurement.create(12.2f, MeasureType.HUMIDITY);
        Measurement measurement2 = Measurement.create(12.2f, MeasureType.HUMIDITY);
        assertTrue(measurement1.hashCode() == measurement2.hashCode());
        assertTrue(measurement1.equals(measurement2));
        assertTrue(measurement2.equals(measurement1));
    }



}
package de.freitag.stefan.sht21.model;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link Measurement}.
 */
public final class MeasurementTest {

    @Test(expected = NullPointerException.class)
    public void createWithNullTypeThrowsNullPointerException() {
        Measurement.create(0.0f, null);
    }

    @Test
    public void createReturnsExpectedObject() {
        final Measurement measurement = Measurement.create(12.2f, MeasureType.HUMIDITY);
        assertEquals(12.2f, measurement.getValue(), 0.00001f);
        assertEquals(MeasureType.HUMIDITY, measurement.getType());
    }

    @Test
    public void equalsWithAnotherMeasurementReturnsFalse() {
        final Measurement measurement1 = Measurement.create(12.2f, MeasureType.HUMIDITY);
        final Measurement measurement2 = Measurement.create(12.2f, MeasureType.TEMPERATURE);
        assertTrue(measurement1.hashCode() != measurement2.hashCode());
        assertFalse(measurement1.equals(measurement2));
        assertFalse(measurement2.equals(measurement1));
    }

    @Test
    public void equalsWithSameMeasurementReturnsTrue() {
        final Measurement measurement1 = Measurement.create(12.2f, MeasureType.HUMIDITY);
        final Measurement measurement2 = Measurement.create(12.2f, MeasureType.HUMIDITY);
        assertTrue(measurement1.hashCode() == measurement2.hashCode());
        assertEquals(measurement1, measurement2);

    }

    @Test
    public void toJsonReturnsExpectedObject() {
        final Measurement measurement = Measurement.create(12.2f, MeasureType.HUMIDITY);
        final String json = measurement.toJson();

        final Gson gson = new Gson();
        final Measurement fromJson = gson.fromJson(json, Measurement.class);
        assertEquals(12.2f, fromJson.getValue(), 0.0001f);
        assertEquals(MeasureType.HUMIDITY, fromJson.getType());
    }

}
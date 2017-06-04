package de.freitag.stefan.sht21.model;

import org.junit.Test;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link Measurement}.
 */
public final class MeasurementTest {


    @Test
    public void createReturnsExpectedObject() {
        final Measurement measurement = Measurement.builder().value(Quantities.getQuantity(12.2f, Units.CELSIUS)).build();
        assertEquals(12.2f, measurement.getValue().getValue());
        assertEquals(Units.CELSIUS, measurement.getValue().getUnit());
    }

    @Test
    public void equalsWithAnotherMeasurementReturnsFalse() {
        final Measurement measurement1 = Measurement.builder().value(Quantities.getQuantity(12.2f, Units.CELSIUS)).build();
        final Measurement measurement2 = Measurement.builder().value(Quantities.getQuantity(12.2f, Units.PERCENT)).build();

        assertTrue(measurement1.hashCode() != measurement2.hashCode());
        assertFalse(measurement1.equals(measurement2));
        assertFalse(measurement2.equals(measurement1));
    }

    @Test
    public void equalsWithSameMeasurementReturnsTrue() {
        final Measurement measurement1 = Measurement.builder().value(Quantities.getQuantity(12.2f, Units.CELSIUS)).build();
        final Measurement measurement2 = Measurement.builder().value(Quantities.getQuantity(12.2f, Units.CELSIUS)).build();

        assertTrue(measurement1.hashCode() == measurement2.hashCode());
        assertEquals(measurement1, measurement2);
    }

}
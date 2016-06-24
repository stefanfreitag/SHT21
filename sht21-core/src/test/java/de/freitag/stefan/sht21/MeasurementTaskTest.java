package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link MeasurementTask}
 */
public final class MeasurementTaskTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithIntervalLessThanMinimumThrowsIllegalArgumentException() {
        new MeasurementTask(1_000L, MeasureType.TEMPERATURE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullMeasureTypeThrowsIllegalArgumentException() {
        new MeasurementTask(10_000L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInterval() {
        final MeasurementTask task = new MeasurementTask(10_000L, MeasureType.TEMPERATURE);
        assertEquals(10_000L, task.getInterval());
    }

}
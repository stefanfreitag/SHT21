package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.task.MeasurementTask;
import de.freitag.stefan.sht21.task.MeasurementTaskListener;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void getInterval() {
        final MeasurementTask task = new MeasurementTask(10_000L, MeasureType.TEMPERATURE);
        assertEquals(10_000L, task.getInterval());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addListenerWithNullThrowsIllegalArgumentException() {
        final MeasurementTask task = new MeasurementTask(10_000L, MeasureType.TEMPERATURE);
        task.addListener(null);
    }

    @Test
    public void addListenerSecondTimeReturnsFalse() {
        final MeasurementTaskListener listener = measurement -> {
            // empty method
        };
        final MeasurementTask task = new MeasurementTask(10_000L, MeasureType.TEMPERATURE);

        assertTrue(task.addListener(listener));
        assertFalse(task.addListener(listener));
    }
}
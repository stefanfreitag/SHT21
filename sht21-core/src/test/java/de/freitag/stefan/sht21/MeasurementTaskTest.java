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
    
    @Test
    public void getInterval() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        assertEquals(10_000L, task.getInterval());
    }

    @Test(expected = NullPointerException.class)
    public void addListenerWithNullThrowsNullPointerException() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        task.addListener(null);
    }

    @Test
    public void addListenerSecondTimeReturnsFalse() {
        final MeasurementTaskListener listener = measurement -> {
            // empty method
        };
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();

        assertTrue(task.addListener(listener));
        assertFalse(task.addListener(listener));
    }

    @Test
    public void isStartedReturnsFalseForNotStartedTask() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        assertFalse(task.isStarted());
    }

    @Test
    public void isStartedReturnsTrueForStartedTask() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        task.start();

        assertTrue(task.isStarted());
    }

    @Test
    public void isCanceledReturnsFalseForNotCanceledTask() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        assertFalse(task.isCanceled());
    }

    @Test
    public void isCanceledReturnsTrueForCanceledTask() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        task.start();
        task.cancel();
        assertTrue(task.isCanceled());
    }
}
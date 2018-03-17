package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.task.MeasurementTask;
import de.freitag.stefan.sht21.task.MeasurementTaskListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link MeasurementTask}
 */
final class MeasurementTaskTest {
    
    @Test
    void getInterval() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        assertEquals(10_000L, task.getInterval());
    }

    @Test
    void addListenerWithNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        task.addListener(null);});
    }

    @Test
    void addListenerSecondTimeReturnsFalse() {
        final MeasurementTaskListener listener = measurement -> {
            // empty method
        };
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();

        assertTrue(task.addListener(listener));
        assertFalse(task.addListener(listener));
    }

    @Test
    void isStartedReturnsFalseForNotStartedTask() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        assertFalse(task.isStarted());
    }

    @Test
    void isStartedReturnsTrueForStartedTask() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        task.start();

        assertTrue(task.isStarted());
    }

    @Test
    void isCanceledReturnsFalseForNotCanceledTask() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        assertFalse(task.isCanceled());
    }

    @Test
    void isCanceledReturnsTrueForCanceledTask() {
        final MeasurementTask task = MeasurementTask.builder().measureType(MeasureType.HUMIDITY).interval(10_000L).build();
        task.start();
        task.cancel();
        assertTrue(task.isCanceled());
    }
}
package de.freitag.stefan.sht21.rest;

import de.freitag.stefan.sht21.SHT21DummyImpl;
import org.junit.Test;

/**
 * Test class for {@link MeasurementTask}.
 *
 * @author Stefan Freitag
 */
public final class MeasurementTaskTest {

    @Test(expected = IllegalArgumentException.class)
    public void createWithNegativeStartDelayThrowsIllegalArgumentException() {
        MeasurementTask.create(new SHT21DummyImpl(), -10, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithTooSmallIntervalThrowsIllegalArgumentException() {
        MeasurementTask.create(new SHT21DummyImpl(), 5, 5);
    }

    @Test(expected = IllegalStateException.class)
    public void getResolutionWithoutTaskExecutionThrowsIllegalStateException() {
        final MeasurementTask task = MeasurementTask.create(new SHT21DummyImpl(), 5, 30);
        task.getResolution();
    }

    @Test(expected = IllegalStateException.class)
    public void getHumidityWithoutTaskExecutionThrowsIllegalStateException() {
        final MeasurementTask task = MeasurementTask.create(new SHT21DummyImpl(), 5, 30);
        task.getHumidity();
    }

    @Test(expected = IllegalStateException.class)
    public void getTemperatureWithoutTaskExecutionThrowsIllegalStateException() {
        final MeasurementTask task = MeasurementTask.create(new SHT21DummyImpl(), 5, 30);
        task.getTemperature();
    }

    @Test(expected = IllegalStateException.class)
    public void getEobStatusWithoutTaskExecutionThrowsIllegalStateException() {
        final MeasurementTask task = MeasurementTask.create(new SHT21DummyImpl(), 5, 30);
        task.getEobStatus();
    }

    @Test(expected = IllegalStateException.class)
    public void getHeaterStatusWithoutTaskExecutionThrowsIllegalStateException() {
        final MeasurementTask task = MeasurementTask.create(new SHT21DummyImpl(), 5, 30);
        task.getHeaterStatus();
    }

    @Test(expected = IllegalStateException.class)
    public void getCreatedAtWithoutTaskExecutionThrowsIllegalStateException() {
        final MeasurementTask task = MeasurementTask.create(new SHT21DummyImpl(), 5, 30);
        task.getCreatedAt();
    }
}

package de.freitag.stefan.sht21.task;

import de.freitag.stefan.sht21.model.Measurement;

/**
 *
 */
@FunctionalInterface
public interface MeasurementTaskListener {

    /**
     * Called whenever a new measurement has been
     * received from the sensor.
     * @param measurement
     */
    void onReceived(Measurement measurement);
}

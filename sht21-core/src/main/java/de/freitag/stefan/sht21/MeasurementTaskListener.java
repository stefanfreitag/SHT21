package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.Measurement;

/**
 *
 */
public interface MeasurementTaskListener {

    void onReceived(Measurement measurement);
}

package de.freitag.stefan.sht21.data;


import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;

public interface Datastore {

    boolean insert(Measurement measurement);

    Measurement getLatest();

    /**
     * @return
     */
    long size(MeasureType measureType);
}
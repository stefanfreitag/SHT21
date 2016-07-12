package de.freitag.stefan.sht21.data;


import de.freitag.stefan.sht21.data.model.MeasurementEntity;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;

import java.util.List;

public interface Datastore {
    /**
     * Insert a new {@link de.freitag.stefan.sht21.data.model.MeasurementEntity}.
     *
     * @param measurement The non-null {@link de.freitag.stefan.sht21.data.model.MeasurementEntity} to add.
     * @return {@code true} if insertion was successful, otherwise
     * {@code false} is returned.
     */
    boolean insert(MeasurementEntity measurement);

    /**
     * Return the latest {@link de.freitag.stefan.sht21.data.model.MeasurementEntity} of the given {@link MeasureType}.
     *
     * @param measureType The non-null {@link MeasureType} to retrieve
     *                    data for.
     * @return Latest {@link Measurement} of the given {@link MeasureType}.
     */
    de.freitag.stefan.sht21.data.model.MeasurementEntity getLatest(MeasureType measureType);


    /**
     * Return the {@link de.freitag.stefan.sht21.data.model.MeasurementEntity}s of the given {@link MeasureType} for the given
     * interval.
     *
     * @param measureType The non-null {@link MeasureType} to retrieve
     *                    data for.
     * @param start Start time of interval in ms since epoch.
     * @param end End time of interval in ms since epoch.
     * @return Measurements belonging to the interval [start, end].
     */
    List<de.freitag.stefan.sht21.data.model.MeasurementEntity> get(MeasureType measureType, final long start, final long end);


    /**
     * Return the number of stored measurements for
     * the given {@code measureType}.
     * @param measureType  The {@link MeasureType}
     * @return Amount of stored measurements for the given
     * measurement type. In case of an error, -1 will be returned.
     */
    long size(MeasureType measureType);

    /**
     * Remove all measurements from the database.
     */
    void clear();
}
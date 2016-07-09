package de.freitag.stefan.sht21.data;


import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;

import java.util.List;

public interface Datastore {
    /**
     * Insert a new {@link Measurement}.
     *
     * @param measurement The non-null {@link Measurement} to add.
     * @return {@code true} if insertion was successful, otherwise
     * {@code false} is returned.
     */
    boolean insert(Measurement measurement);

    /**
     * Return the latest {@link Measurement} of the given {@link MeasureType}.
     *
     * @param measureType The non-null {@link MeasureType} to retrieve
     *                    data for.
     * @return Latest {@link Measurement} of the given {@link MeasureType}.
     */
    Measurement getLatest(MeasureType measureType);


    /**
     * Return the latest {@link Measurement} of the given {@link MeasureType}.
     *
     * @param measureType The non-null {@link MeasureType} to retrieve
     *                    data for.
     * @return Measurements belonging to the interval [start, end].
     */
    List<Measurement> get(MeasureType measureType, final long start, final long end);


    /**
     * Return the number of stored measurements for
     * the given {@code measureType}.
     * @param measureType  The {@link MeasureType}
     * @return Amount of stored measurements for the given
     * measurement type.
     */
    long size(MeasureType measureType);
}
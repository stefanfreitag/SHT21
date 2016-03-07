package de.freitag.stefan.sht21.export;

import de.freitag.stefan.sht21.model.Measurement;

import java.util.List;

/**
 * Interface to implement by all exporters.
 */
public interface Exporter {
    /**
     * Pass the {@link Measurement} objects to export to the exporter.
     * The data items will be processed/ exported in order.
     *
     * @param measurements The {@link Measurement} objects to export.
     */
    void setData(List<Measurement> measurements);

    /**
     * Starts the process of exporting data.
     *
     * @return {@code true} if the export is successful, otherwise
     * {@code false} is returned.
     */
    boolean export();

}

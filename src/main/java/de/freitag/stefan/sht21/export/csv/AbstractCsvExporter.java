package de.freitag.stefan.sht21.export.csv;

import de.freitag.stefan.sht21.export.AbstractExporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

/**
 * Base class for all exporters with csv output.
 */
abstract class AbstractCsvExporter extends AbstractExporter {

    /**
     * The {@link Logger} for this class.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AbstractCsvExporter.class.getCanonicalName());

    /**
     * Create a new {@link AbstractCsvExporter}.
     *
     * @param name        The non-null and non-empty name of the exporter.
     * @param description The non-null description of the exporter.
     * @param path        The {@link Path} for the output file.
     */
    AbstractCsvExporter(final String name, final String description, final Path path) {
        super(name, description, path);
    }

}

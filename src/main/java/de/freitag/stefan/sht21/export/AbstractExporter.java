package de.freitag.stefan.sht21.export;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Base class for all exporters.
 */
public abstract class AbstractExporter implements Exporter {

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOG = LogManager.getLogger(AbstractExporter.class.getCanonicalName());
    /**
     * The {@link Path} to the output file.
     */
    private final Path filename;
    /**
     * The name of this exporter.
     */
    private String name;
    /**
     * A brief description about the purpose of this exporter.
     */
    private String description;

    /**
     * Create a new {@link AbstractExporter}
     *
     * @param name        The non-null and non-empty name of the exporter.
     * @param description The non-null description of the exporter.
     * @param filename    The non-null path of the export file.
     */
    protected AbstractExporter(final String name, final String description, final Path filename) {
        Objects.requireNonNull(name, "Name of exporter has to be non-null.");
        Objects.requireNonNull(name, "Description of exporter has to be non-null.");
        Objects.requireNonNull(name, "Description of exporter has to be non-null.");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Empty exporter name is not allowed.");
        }
        if (!isValidPath(filename)) {
            throw new IllegalArgumentException("Not a valid path:" + (filename == null ? "null" : filename.toAbsolutePath()));
        }
        this.name = name;
        this.description = description;
        this.filename = filename;
    }

    /**
     * Checks for a valid {@link Path}.
     *
     * @param path The {@link Path} to check.
     * @return {@code true} if {@code path} is valid, otherwise {@code false} is returned.
     */
    private static boolean isValidPath(final Path path) {
        return path != null && !Files.isDirectory(path);
    }

    /**
     * Return the exporter name.
     *
     * @return Name of the exporter.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Return the description for the exporter.
     *
     * @return Description for the exporter.
     */
    public final String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "AbstractExporter{" +
                "description='" + description + '\'' +
                ", filename=" + filename +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractExporter that = (AbstractExporter) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getFilename(), that.getFilename());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getFilename());
    }

    /**
     * Split the given measurements based on the {@link de.freitag.stefan.sht21.model.MeasureType}
     *
     * @param measurements A non-null list of measurements.
     * @return Measurements split up in temperature measurements and humidity measurements.
     */
    protected Map<MeasureType, List<Measurement>> splitMeasurements(final List<Measurement> measurements) {
        assert measurements != null;
        final List<Measurement> temperatureMeasurements = new ArrayList<>();
        final List<Measurement> humidityMeasurements = new ArrayList<>();

        for (final Measurement measurement : measurements) {
            if (MeasureType.TEMPERATURE.equals(measurement.getType())) {
                temperatureMeasurements.add(measurement);
            } else if (MeasureType.HUMIDITY.equals(measurement.getType())) {
                humidityMeasurements.add(measurement);
            } else {
                LOG.error("Unsupported measure type found. Type :" + measurement.getType());
            }
        }
        final Map<MeasureType, List<Measurement>> splitLists = new HashMap<>();
        splitLists.put(MeasureType.TEMPERATURE, temperatureMeasurements);
        splitLists.put(MeasureType.HUMIDITY, humidityMeasurements);
        return splitLists;
    }

    protected Path getFilename() {
        return this.filename;
    }
}

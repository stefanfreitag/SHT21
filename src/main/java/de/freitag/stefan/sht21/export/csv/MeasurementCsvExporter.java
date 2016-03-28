package de.freitag.stefan.sht21.export.csv;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


public final class MeasurementCsvExporter extends AbstractCsvExporter {

    /**
     * CSV separator.
     */
    private static final char SEPARATOR = ';';
    /**
     * The name of this exporter.
     */
    private static final String NAME = "CSV Exporter";
    /**
     * The description for this exporter.
     */
    private static final String DESCRIPTION = "Exports temperature and humidity measurements to a CSV files";
    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOG = LogManager.getLogger(MeasurementCsvExporter.class.getCanonicalName());

    /**
     * Contains the measurements split by {@link MeasureType}.
     */
    private Map<MeasureType, List<Measurement>> map;


    /**
     * Create a new {@link MeasurementCsvExporter}.
     *
     * @param path The {@link Path} for the output file.
     */
    protected MeasurementCsvExporter(final Path path) {
        super(NAME, DESCRIPTION, path);
    }


    /**
     * Pass the {@link Measurement} objects to export to the exporter.
     * The data items will be processed/ exported in order.
     *
     * @param measurements The {@link Measurement} objects to export.
     */
    @Override
    public void setData(final List<Measurement> measurements) {
        if (measurements == null) {
            throw new IllegalArgumentException("Measurements are null");
        }
        this.map = splitMeasurements(measurements);

    }

    @Override
    public boolean export() {
        if (!map.get(MeasureType.HUMIDITY).isEmpty()) {
            this.writeToCsvFile(map.get(MeasureType.HUMIDITY), MeasureType.HUMIDITY);
        }
        if (!map.get(MeasureType.TEMPERATURE).isEmpty()) {
            this.writeToCsvFile(map.get(MeasureType.TEMPERATURE), MeasureType.TEMPERATURE);
        }
        return true;
    }

    private boolean writeToCsvFile(final List<Measurement> measurements, final MeasureType measureType) {
        assert measurements != null;
        assert measureType != null;

        BufferedWriter writer = null;
        try {
            String xxx = createFileName(measureType);


            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(xxx), "UTF-8"
            ));


            writer.write("timestamp");
            writer.write(SEPARATOR);
            writer.write("value");
            writer.write(SEPARATOR);
            writer.write("unit");

            String unit;
            if (measureType == MeasureType.HUMIDITY) {
                unit = "%RH";
            } else {
                unit = "degC";
            }

            writer.write(System.lineSeparator());


            for (final Measurement measurement : measurements) {
                writer.write(String.valueOf(measurement.getCreatedAt().getTime()));
                writer.write(';');
                writer.write(String.valueOf(measurement.getValue()));
                writer.write(';');
                writer.write(unit);
                writer.write(System.lineSeparator());
            }
            return true;
        } catch (final IOException exception) {
            LOG.error(exception.getMessage(), exception);
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (final IOException exception) {
                    LOG.error(exception.getMessage(), exception);
                }
            }
        }
    }

    private String createFileName(final MeasureType measureType) {
        assert measureType != null;

        String name = this.getFilename().toString();
        if (measureType == MeasureType.HUMIDITY) {
            name = "humidity_" + name;
        } else if (measureType == MeasureType.TEMPERATURE) {
            name = "temperature_" + name;
        }
        return name;
    }
}

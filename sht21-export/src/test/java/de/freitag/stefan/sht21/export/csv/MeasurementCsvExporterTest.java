package de.freitag.stefan.sht21.export.csv;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Test class for {@link MeasurementCsvExporter}.
 */
public final class MeasurementCsvExporterTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    //@SuppressWarnings("unused")
    @Test
    public void dummyDataExport() throws Exception {
        final Path outputPath = Paths.get("output.csv");
        final MeasurementCsvExporter exporter = new MeasurementCsvExporter(outputPath);
        final List<Measurement> measurements = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            final Measurement measurement = Measurement.create(ThreadLocalRandom.current().nextInt(12, 18 + 1), MeasureType.TEMPERATURE);
            measurements.add(measurement);
        }
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            final Measurement measurement = Measurement.create(ThreadLocalRandom.current().nextInt(12, 18 + 1), MeasureType.HUMIDITY);
            measurements.add(measurement);
        }
        exporter.setData(measurements);
        exporter.export();
    }

    @Test
    public void getNameDoesNotReturnNull() {
        try {
            final String filePath = testFolder.newFile().toString();
            final MeasurementCsvExporter exporter = new MeasurementCsvExporter(Paths.get(filePath));
            assertNotNull(exporter.getName());
        } catch (final IOException exception) {
            fail();
        }
    }

    @Test
    public void getDescriptionDoesNotReturnNull() {
        try {
            final String filePath = testFolder.newFile().toString();
            final MeasurementCsvExporter exporter = new MeasurementCsvExporter(Paths.get(filePath));
            assertNotNull(exporter.getDescription());
        } catch (final IOException exception) {
            fail();
        }
    }
}
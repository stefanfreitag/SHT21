package de.freitag.stefan.sht21.export.pdf;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Test class for {@link MeasurementPdfExporter}.
 */
public final class MeasurementPdfExporterTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @SuppressWarnings("unused")
    public void xxx() throws InterruptedException, IOException {
        final MeasurementPdfExporter exporter = new MeasurementPdfExporter(testFolder.newFile().toPath());
        final List<Measurement> measurements = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1);
            final Measurement measurement = Measurement.create(ThreadLocalRandom.current().nextInt(12, 18 + 1), MeasureType.TEMPERATURE);
            measurements.add(measurement);
        }
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            final Measurement measurement = Measurement.create(ThreadLocalRandom.current().nextInt(12, 18 + 1), MeasureType.HUMIDITY);
            measurements.add(measurement);
        }
        exporter.setData(measurements);


    }

    @Test
    public void getNameDoesNotReturnNull() {
        try {
            final String filePath = testFolder.newFile().toString();
            final MeasurementPdfExporter exporter = new MeasurementPdfExporter(Paths.get(filePath));
            assertNotNull(exporter.getName());
        } catch (final IOException exception) {
            fail();
        }
    }

    @Test
    public void getDescriptionDoesNotReturnNull() {
        try {
            final String filePath = testFolder.newFile().toString();
            final MeasurementPdfExporter exporter = new MeasurementPdfExporter(Paths.get(filePath));
            assertNotNull(exporter.getDescription());
        } catch (final IOException exception) {
            fail();
        }
    }
}
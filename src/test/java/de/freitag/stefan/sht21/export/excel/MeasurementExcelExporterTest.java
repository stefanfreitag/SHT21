package de.freitag.stefan.sht21.export.excel;

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
 * Test class for {@link MeasurementExcelExporter}.
 */
public final class MeasurementExcelExporterTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);


    @SuppressWarnings("unused")
    public void dummyDataExport() throws Exception {
        final Path outputPath = Paths.get("workbook.xlsx");
        final MeasurementExcelExporter exporter = new MeasurementExcelExporter(outputPath);
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
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullPathThrowsIllegalArgumentException() {
        new MeasurementExcelExporter(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithDirectoryAsPathThrowsIllegalArgumentException() {
        new MeasurementExcelExporter(Paths.get(testFolder.getRoot().toString()));
    }

    @Test
    public void getNameDoesNotReturnNull() {
        try {
            final String filePath = testFolder.newFile().toString();
            final MeasurementExcelExporter exporter = new MeasurementExcelExporter(Paths.get(filePath));
            assertNotNull(exporter.getName());
        } catch (final IOException exception) {
            fail();
        }
    }

    @Test
    public void getDescriptionDoesNotReturnNull() {
        try {
            final String filePath = testFolder.newFile().toString();
            final MeasurementExcelExporter exporter = new MeasurementExcelExporter(Paths.get(filePath));
            assertNotNull(exporter.getDescription());
        } catch (final IOException exception) {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDataWithNullThrowsIllegalArgumentException() {
        try {
            final String filePath = testFolder.newFile().toString();
            final MeasurementExcelExporter exporter = new MeasurementExcelExporter(Paths.get(filePath));
            exporter.setData(null);
        } catch (final IOException exception) {
            fail();
        }
    }
}
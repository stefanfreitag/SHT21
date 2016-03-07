package de.freitag.stefan.sht21.export.excel;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Test class for {@link MeasurementExcelExporter}.
 */
public final class MeasurementExcelExporterTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    public void dummyDataExport() throws Exception {
        Path outputPath = Paths.get("workbook.xlsx");
        final MeasurementExcelExporter exporter = new MeasurementExcelExporter(outputPath);
        List<Measurement> measurements = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            Measurement measurement = Measurement.create(ThreadLocalRandom.current().nextInt(12, 18 + 1), MeasureType.TEMPERATURE);
            measurements.add(measurement);
        }
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            Measurement measurement = Measurement.create(ThreadLocalRandom.current().nextInt(12, 18 + 1), MeasureType.HUMIDITY);
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
}
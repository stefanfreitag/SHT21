package de.freitag.stefan.sht21.export.pdf;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by stefan on 08.03.16.
 */
public class AbstractPdfExporterTest {

    @Test
    public void xxx() throws InterruptedException {


        MeasurementPdfExporter exporter = new MeasurementPdfExporter("xxx", "xxx");
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
}
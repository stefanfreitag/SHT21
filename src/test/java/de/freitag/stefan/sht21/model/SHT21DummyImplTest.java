package de.freitag.stefan.sht21.model;

import de.freitag.stefan.sht21.SHT21;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Test class for {@link de.freitag.stefan.sht21.model.SHT21DummyImpl}.
 * @author Stefan Freitag
 */
public final class SHT21DummyImplTest {

    private SHT21 sht21 = new SHT21DummyImpl();

    private static Logger getLogger() {
        return LogManager.getLogger(SHT21DummyImpl.class.getCanonicalName());
    }

    @Test
    public void testGetResolution() {
        getLogger().info(sht21.getResolution());
    }

    @Test
    public void testGetEndOfBatteryAlert() {
        getLogger().info(sht21.getEndOfBatteryAlert());
    }

    @Test
    public void testGetHeaterStatus() {
        getLogger().info(sht21.getHeaterStatus());
    }

    @Test
    public void testMeasurePoll() {
        getLogger().info(sht21.measurePoll(MeasureType.TEMPERATURE));
    }
}
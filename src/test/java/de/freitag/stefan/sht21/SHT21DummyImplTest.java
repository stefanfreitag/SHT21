package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test class for {@link SHT21DummyImpl}.
 * @author Stefan Freitag
 */
public final class SHT21DummyImplTest {

    /**
     * SHT21 dummy implementation to use.
     */
    private final SHT21 sht21 = new SHT21DummyImpl();

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(SHT21DummyImpl.class.getCanonicalName());
    }

    @Test
    public void getResolutionReturnsNotNull() {
        assertNotNull(sht21.getResolution());
    }

    @Test
    public void getEndOfBatteryAlertReturnsNotNull() {
        assertNotNull(sht21.getEndOfBatteryAlert());
    }

    @Test
    public void getHeaterStatusReturnsNotNull() {
        assertNotNull(sht21.getHeaterStatus());
    }

    @Test
    public void measurePollReturnsNotNull() {
        assertNotNull(sht21.measurePoll(MeasureType.TEMPERATURE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void measurePollWithNullMeasureTypeThrowsIllegalArgumentException() {
        sht21.measurePoll(null);
    }
}
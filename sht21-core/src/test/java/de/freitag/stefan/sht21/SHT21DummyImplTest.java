package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.UnsupportedMeasureTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link SHT21DummyImpl}.
 * @author Stefan Freitag
 */
final class SHT21DummyImplTest {

    /**
     * SHT21 dummy implementation to use.
     */
    private final SHT21 sht21 = new SHT21DummyImpl();

    @Test
    void getResolutionReturnsNotNull() {
        assertNotNull(sht21.getResolution());
    }

    @Test
    void getEndOfBatteryAlertReturnsNotNull() {
        assertNotNull(sht21.getEndOfBatteryAlert());
    }

    @Test
    void getHeaterStatusReturnsNotNull() {
        assertNotNull(sht21.getHeaterStatus());
    }

    @Test
    void measurePollReturnsNotNull() throws UnsupportedMeasureTypeException {
        assertNotNull(sht21.measurePoll(MeasureType.TEMPERATURE));
    }

    @Test
    void measurePollWithNullMeasureTypeThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> sht21.measurePoll(null));
    }
}
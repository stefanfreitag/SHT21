package de.freitag.stefan.sht21.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link EndOfBatteryAlert}.
 */
public final class EndOfBatteryAlertTest {

    @Test
    public void getEOBAlertReturnsExpectedValues() {
        assertEquals(EndOfBatteryAlert.EOB_ALERT_ON, EndOfBatteryAlert.getEOBAlert((byte) 0x41));
        assertEquals(EndOfBatteryAlert.EOB_ALERT_OFF, EndOfBatteryAlert.getEOBAlert((byte) 0x22));
    }

    @Test
    public void toStringReturnsExpectedValue() {
        assertTrue("EOB_ALERT_ON".equalsIgnoreCase(EndOfBatteryAlert.EOB_ALERT_ON.toString()));
        assertTrue("EOB_ALERT_OFF".equalsIgnoreCase(EndOfBatteryAlert.EOB_ALERT_OFF.toString()));
    }

    @Test
    public void getByteReturnsExpectedValue() {
        assertEquals(0x40, EndOfBatteryAlert.EOB_ALERT_ON.getByte());
        assertEquals(0x00, EndOfBatteryAlert.EOB_ALERT_OFF.getByte());
    }
}
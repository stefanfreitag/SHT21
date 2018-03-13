package de.freitag.stefan.sht21.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link EndOfBatteryAlert}.
 */
final class EndOfBatteryAlertTest {

    @Test
    void getEOBAlertReturnsExpectedValues() {
        assertEquals(EndOfBatteryAlert.EOB_ALERT_ON, EndOfBatteryAlert.getEOBAlert((byte) 0x41));
        assertEquals(EndOfBatteryAlert.EOB_ALERT_OFF, EndOfBatteryAlert.getEOBAlert((byte) 0x22));
    }

    @Test
    void toStringReturnsExpectedValue() {
        System.out.println(EndOfBatteryAlert.EOB_ALERT_ON.toString());
        assertTrue("EOB_ALERT_ON".equalsIgnoreCase(EndOfBatteryAlert.EOB_ALERT_ON.toString()));
        assertTrue("EOB_ALERT_OFF".equalsIgnoreCase(EndOfBatteryAlert.EOB_ALERT_OFF.toString()));
    }

    @Test
    void getByteReturnsExpectedValue() {
        assertEquals(0x40, EndOfBatteryAlert.EOB_ALERT_ON.getEobByte());
        assertEquals(0x00, EndOfBatteryAlert.EOB_ALERT_OFF.getEobByte());
    }
}
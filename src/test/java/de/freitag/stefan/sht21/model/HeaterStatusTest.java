package de.freitag.stefan.sht21.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link HeaterStatus}.
 */
public final class HeaterStatusTest {

    @Test
    public void getStatusReturnsExpectedValue() {
        assertEquals(HeaterStatus.HEATER_OFF, HeaterStatus.getStatus((byte) 0x01));
        assertEquals(HeaterStatus.HEATER_ON, HeaterStatus.getStatus((byte) 0xFE));
    }
}
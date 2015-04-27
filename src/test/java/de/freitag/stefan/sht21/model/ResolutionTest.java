package de.freitag.stefan.sht21.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link Resolution}.
 */
public final class ResolutionTest {

    @Test
    public void getResolutionReturnsExpectedValues() {
        assertEquals(Resolution.RES_12_14BIT, Resolution.getResolution((byte) 0x20));
        assertEquals(Resolution.RES_8_12BIT, Resolution.getResolution((byte) 0x21));
        assertEquals(Resolution.RES_10_13BIT, Resolution.getResolution((byte) 0x84));
        assertEquals(Resolution.RES_11_11BIT, Resolution.getResolution((byte) 0x83));
    }

}
package de.freitag.stefan.sht21.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link Resolution}.
 */
public final class ResolutionTest {

    @Test
    public void getResolution12And14ReturnsExpectedValues() {
        assertEquals(Resolution.RES_12_14BIT, Resolution.getResolution((byte) 0x20));
    }

    @Test
    public void getResolution8And12ReturnsExpectedValues() {
        assertEquals(Resolution.RES_8_12BIT, Resolution.getResolution((byte) 0x21));
    }

    @Test
    public void getResolution10And13ReturnsExpectedValues() {
        assertEquals(Resolution.RES_10_13BIT, Resolution.getResolution((byte) 0x84));
    }

    @Test
    public void getResolution11And11ReturnsExpectedValues() {
        assertEquals(Resolution.RES_11_11BIT, Resolution.getResolution((byte) 0x83));
    }
}
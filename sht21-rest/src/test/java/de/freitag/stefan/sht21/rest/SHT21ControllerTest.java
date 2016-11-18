package de.freitag.stefan.sht21.rest;

import org.junit.Test;

/**
 * Test class for {@link SHT21Controller}.
 *
 * @author Stefan Freitag
 */
public final class SHT21ControllerTest {

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullThrowsIllegalArgumentException() {
        SHT21Controller.create(null);
    }
}
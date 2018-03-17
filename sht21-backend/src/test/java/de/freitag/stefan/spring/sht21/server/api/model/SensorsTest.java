package de.freitag.stefan.spring.sht21.server.api.model;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class SensorsTest {

    @Test
    public void isValidUuidWithNullReturnsFalse() {
        assertFalse(Sensors.isValidUuid(null));
    }

    @Test
    public void isValidUuidWithUuidV4ReturnsTrue() {
        assertTrue(Sensors.isValidUuid("3c6bf1df-21bd-4dd9-bceb-34d2c0e1a900"));
    }
}
package de.freitag.stefan.spring.sht21.server.api.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

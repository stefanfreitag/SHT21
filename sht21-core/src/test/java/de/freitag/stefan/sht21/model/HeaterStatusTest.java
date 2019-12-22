package de.freitag.stefan.sht21.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Test class for {@link HeaterStatus}. */
final class HeaterStatusTest {

  @Test
  void getStatusReturnsExpectedValue() {
    assertEquals(HeaterStatus.HEATER_OFF, HeaterStatus.getStatus((byte) 0x01));
    assertEquals(HeaterStatus.HEATER_ON, HeaterStatus.getStatus((byte) 0xFE));
  }
}

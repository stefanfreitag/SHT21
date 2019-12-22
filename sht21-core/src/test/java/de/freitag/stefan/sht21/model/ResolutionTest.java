package de.freitag.stefan.sht21.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Test class for {@link Resolution}. */
final class ResolutionTest {

  @Test
  void getResolution12And14ReturnsExpectedValues() {
    assertEquals(Resolution.RES_12_14BIT, Resolution.getResolution((byte) 0x20));
  }

  @Test
  void getResolution8And12ReturnsExpectedValues() {
    assertEquals(Resolution.RES_8_12BIT, Resolution.getResolution((byte) 0x21));
  }

  @Test
  void getResolution10And13ReturnsExpectedValues() {
    assertEquals(Resolution.RES_10_13BIT, Resolution.getResolution((byte) 0x84));
  }

  @Test
  void getResolution11And11ReturnsExpectedValues() {
    assertEquals(Resolution.RES_11_11BIT, Resolution.getResolution((byte) 0x83));
  }
}

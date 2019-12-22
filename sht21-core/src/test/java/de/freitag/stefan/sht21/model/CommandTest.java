package de.freitag.stefan.sht21.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Test class for {@link Command}. */
final class CommandTest {

  @Test
  void getCommandByteReturnsExpectedValue() {
    assertEquals((byte) 0xfe, Command.SOFT_RESET.getCommandByte());
  }
}

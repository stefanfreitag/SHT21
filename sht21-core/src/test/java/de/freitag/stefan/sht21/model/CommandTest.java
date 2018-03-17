package de.freitag.stefan.sht21.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link Command}.
 */
final class CommandTest {

    @Test
    void getCommandByteReturnsExpectedValue() {
        assertEquals((byte) 0xfe, Command.SOFT_RESET.getCommandByte());
    }
}
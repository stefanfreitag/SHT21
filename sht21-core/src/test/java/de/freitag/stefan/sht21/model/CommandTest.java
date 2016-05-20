package de.freitag.stefan.sht21.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link Command}.
 */
public final class CommandTest {

    @Test
    public void getCommandByteReturnsExpectedValue() {
        assertEquals((byte) 0xfe, Command.SOFT_RESET.getCommandByte());
    }
}
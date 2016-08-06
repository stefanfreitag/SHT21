
package de.freitag.stefan.sht21;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link SHT21Impl}.
 * @author Stefan Freitag
 */
public final class SHT21ImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void createWithInvalidBusNrThrowsIllegalArgumentException() {
        SHT21Impl.create(-1, 0x40);
    }

    /**
     * Byte values taken from data sheet.
     */
    @Test
    public void calcRHReturnsExpectedValues() {
        final ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put((byte) 0x63);
        buffer.put((byte) 0x52);
        assertEquals(42.493416, SHT21Impl.calcRH(buffer), 0.01f);
    }

    @Test
    public void calcRHReturnsSecondExpectedValues() {
        final ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put((byte) 0x8D);
        buffer.put((byte) 0xCE);
        assertEquals(63.23675537109375, SHT21Impl.calcRH(buffer), 0.01f);
    }


    @Test
    public void calcTemperatureCReturnsExpectedValues() {
        final ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put((byte) 0x63);
        buffer.put((byte) 0x8c);
        assertEquals(21.479599, SHT21Impl.calcTemperatureC(buffer), 0.0001f);
    }


    @Test
    public void checkCrCReturnsExpectedResultWithExample1() {
        final byte[] bytes = new byte[3];
        bytes[0] = (byte) 0x63;
        bytes[1] = 0x52;
        bytes[2] = 0x64;
        assertTrue(SHT21Impl.checkCrC(bytes));
    }

    @Test
    public void checkCrCReturnsExpectedResultWithExample2() {
        final byte[] bytes = new byte[3];
        bytes[0] = (byte) 0x61;
        bytes[1] = (byte) 0xD4;
        bytes[2] = (byte) 0x61;
        assertTrue(SHT21Impl.checkCrC(bytes));
    }
}




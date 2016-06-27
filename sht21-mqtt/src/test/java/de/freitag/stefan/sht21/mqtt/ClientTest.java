package de.freitag.stefan.sht21.mqtt;

import org.junit.Test;

/**
 * Test class for {@link Client}.
 */
public final class ClientTest {
    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullThrowsIllegalArgumentException() {
        new Client(null);
    }

    @Test
    public void XXX() {
        final Client client = new Client(new Configuration(
                "tcp://192.168.178.44", "domain", "deviceId"
        ));
        //sending client.xxx();
    }
}
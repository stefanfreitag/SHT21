package de.freitag.stefan.sht21.mqtt;

import org.junit.Test;

/**
 * Test class for {@link Configuration}.
 */
public final class ConfigurationTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullBrokerUrlThrowsIllegalArgumentException() {
        new Configuration(null, "domain", "deviceId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyBrokerUrlThrowsIllegalArgumentException() {
        new Configuration("", "domain", "deviceId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullDeviceIdThrowsIllegalArgumentException() {
        new Configuration("tcp://localhost:1883", "domain", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyDeviceIdThrowsIllegalArgumentException() {
        new Configuration("tcp://q.m2m.io:1883", "domain", "");
    }

}
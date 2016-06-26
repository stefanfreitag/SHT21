package de.freitag.stefan.sht21.mqtt;

import java.util.Objects;

/**
 * MQTT configuration.
 */
public final class Configuration {

    private final String brokerUrl;
    private final String domain;
    private final String deviceId;

    /**
     * Create a new {@link Configuration}.
     *
     * @param brokerUrl A non-null broker URL.
     * @param domain The MQTT domain.
     * @param deviceId  Unique identifier for the device.
     */
    public Configuration(final String brokerUrl, final String domain, final String deviceId) {
        if (brokerUrl == null) {
            throw new IllegalArgumentException("Broker URL is null");
        }
        if (brokerUrl.isEmpty()) {
            throw new IllegalArgumentException("Broker URL is empty");
        }

        if (deviceId == null) {
            throw new IllegalArgumentException("Device id is null");
        }
        if (deviceId.isEmpty()) {
            throw new IllegalArgumentException("Device id is empty");
        }
        this.brokerUrl = brokerUrl;
        this.domain = domain;
        this.deviceId = deviceId;
    }

    public String getBrokerUrl() {
        return this.brokerUrl;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return Objects.equals(getBrokerUrl(), that.getBrokerUrl()) &&
                Objects.equals(getDomain(), that.getDomain()) &&
                Objects.equals(getDeviceId(), that.getDeviceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBrokerUrl(), getDomain(), getDeviceId());
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "brokerUrl='" + brokerUrl + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}

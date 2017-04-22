package de.freitag.stefan.sht21.mqtt;

import de.freitag.stefan.sht21.model.Measurement;

public interface SHT21Client {
    /**
     * Send a {@link Measurement} via MQTT.
     *
     * @param measurement The non-null {@link Measurement} to send.
     */
    void send(Measurement measurement);
}
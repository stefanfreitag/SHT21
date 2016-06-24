package de.freitag.stefan.sht21.mqtt;

import de.freitag.stefan.sht21.model.Measurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;

public class Client implements SHT21Client, MqttCallback {

    /**
     * The keep alive interval in seconds.
     */
    private static final int KEEP_ALIVE_INTERVAL = 30;
    private final Configuration configuration;
    private MqttClient myClient;
    private MqttConnectOptions connOpt;

    /**
     * Create a new {@link Client}.
     *
     * @param configuration A non-null {@link Configuration} for the client.
     */
    public Client(final Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration is null");
        }
        this.configuration = configuration;
        this.setupConnectionOptions();
        this.connect();
    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(Client.class.getCanonicalName());
    }

    private void sendMessage() {

    }

    private MqttMessage createMessage() {
        String pubMsg = "{\"pubmsg\":" + "}";
        int pubQoS = 0;
        final MqttMessage message = new MqttMessage(pubMsg.getBytes());
        message.setQos(pubQoS);
        message.setRetained(false);
        return message;
    }

    private void connect() {
        final String clientID = this.configuration.getDeviceId();
        try {
            this.myClient = new MqttClient(this.configuration.getBrokerUrl(), clientID);
            this.myClient.setCallback(this);
            this.myClient.connect(this.connOpt);
            getLogger().info("Connected to " + this.configuration.getBrokerUrl());
        } catch (final MqttException exception) {
            getLogger().error(exception.getMessage(), exception);
        }
    }

    private void disconnect() {
        try {
            this.myClient.disconnect();
            getLogger().info("Disconnected from " + this.configuration.getBrokerUrl());
        } catch (final MqttException exception) {
            getLogger().error(exception.getMessage(), exception);
        }
    }

    private void setupConnectionOptions() {
        this.connOpt = new MqttConnectOptions();
        this.connOpt.setCleanSession(true);
        this.connOpt.setKeepAliveInterval(KEEP_ALIVE_INTERVAL);
    }

    /**
     * connectionLost
     * This callback is invoked upon losing the MQTT connection.
     */
    @Override
    public void connectionLost(final Throwable throwable) {
        getLogger().error("Connection lost.", throwable.getCause());
        // TODO: code to reconnect to the broker would go here if desired
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) {
        System.out.println("-------------------------------------------------");
        System.out.println("| Topic:" + topic);
        System.out.println("| Message: " + new String(message.getPayload()));
        System.out.println("-------------------------------------------------");
    }

    /**
     * Called when delivery for a message has been completed, and all
     * acknowledgments have been received. For QoS 0 messages it is
     * called once the message has been handed to the network for
     * delivery. For QoS 1 it is called when PUBACK is received and
     * for QoS 2 when PUBCOMP is received. The token will be the same
     * token as that returned when the message was published.
     *
     * @param token the delivery token associated with the message.
     */
    @Override
    public void deliveryComplete(final IMqttDeliveryToken token) {
        try {
            getLogger().info("Publishing complete" + token.getMessage().getPayload());
        } catch (final MqttException exception) {
            getLogger().error(exception.getMessage(), exception);
        }
    }

    /**
     * Send a {@link Measurement} via MQTT.
     *
     * @param measurement The non-null {@link Measurement} to send.
     */
    @Override
    public void send(final Measurement measurement) {
        final String myTopic = this.configuration.getDomain() + "/" + this.configuration.getDeviceId();
        final MqttTopic topic = myClient.getTopic(myTopic);
        final MqttMessage message = createMessage();

        try {
            final MqttDeliveryToken token = topic.publish(message);
            token.waitForCompletion();
            Thread.sleep(100);
        } catch (final MqttException | InterruptedException exception) {
            getLogger().error(exception.getMessage(), exception);
        }
        disconnect();
    }
}
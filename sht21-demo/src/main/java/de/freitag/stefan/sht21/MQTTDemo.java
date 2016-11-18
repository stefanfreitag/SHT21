package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.mqtt.Client;
import de.freitag.stefan.sht21.mqtt.Configuration;
import de.freitag.stefan.sht21.task.MeasurementTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public final class MQTTDemo {

    /**
     * Application entry point
     *
     * @param args Arguments passed along at the command line.
     */
    public static void main(final String[] args) {
        final MQTTDemo instance = new MQTTDemo();
        instance.doMain(args);
        System.exit(0);
    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(MQTTDemo.class.getCanonicalName());
    }

    private void doMain(final String[] args) {

        final Configuration configuration = new Configuration("tcp://192.168.178.44", "domain", "deviceId");
        final Client client = new Client(configuration);
        final MeasurementTask task = new MeasurementTask(5_000L, MeasureType.TEMPERATURE);
        task.addListener(measurement -> {
            getLogger().info("Sending measurement:" + measurement);
            client.send(measurement);
        });
        task.start();


        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (final InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }


}

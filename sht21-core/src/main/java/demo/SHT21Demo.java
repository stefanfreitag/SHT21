package demo;

import com.pi4j.io.i2c.I2CBus;
import de.freitag.stefan.sht21.SHT21;
import de.freitag.stefan.sht21.SHT21Impl;
import de.freitag.stefan.sht21.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.concurrent.TimeUnit;

public final class SHT21Demo {

    /**
     * Device address.
     */
    private static final int I2C_ADDRESS = 0x40;

    private SHT21 sht21;

    @Option(name = "-demoType", usage = "Allowed values: battery, heater, humidity, resolution, temperature", required = true)
    private String demoType;

    /**
     * Application entry point
     *
     * @param args Arguments passed along at the command line.
     */
    public static void main(final String[] args) {
        final SHT21Demo instance = new SHT21Demo();
        instance.doMain(args);
        System.exit(0);
    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(SHT21Demo.class.getCanonicalName());
    }

    private void doMain(final String[] args) {
        final CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException exception) {
            System.err.println(exception.getMessage());
            parser.printUsage(System.err);
            return;
        }

        this.sht21 = SHT21Impl.create(I2CBus.BUS_1, I2C_ADDRESS);

        while (true) {
            try {
                this.execute();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void execute() {
        if ("battery".equalsIgnoreCase(demoType)) {
            final EndOfBatteryAlert eobAlert = sht21.getEndOfBatteryAlert();
            getLogger().info("End of battery alert: " + eobAlert);
        } else if ("humidity".equalsIgnoreCase(demoType)) {
            final Measurement measurement;
            try {
                measurement = sht21.measurePoll(MeasureType.HUMIDITY);
                getLogger().info("Relative Humidity [%]: " + String.format("%.2f", measurement.getValue()));
            } catch (final UnsupportedMeasureTypeException exception) {
                getLogger().error(exception.getMessage(), exception);
            }

        } else if ("resolution".equalsIgnoreCase(demoType)) {
            final Resolution resolution = sht21.getResolution();
            getLogger().info("Resolution: " + resolution);
        } else if ("heater".equalsIgnoreCase(demoType)) {
            final HeaterStatus eobAlert = sht21.getHeaterStatus();
            getLogger().info("Heater status: " + eobAlert);
        } else if ("temperature".equalsIgnoreCase(demoType)) {
            final Measurement measurement;
            try {
                measurement = sht21.measurePoll(MeasureType.TEMPERATURE);
                getLogger().info("Measured temperature [deg C]: " + String.format("%.2f", measurement.getValue()));
            } catch (final UnsupportedMeasureTypeException exception) {
                getLogger().error(exception.getMessage(), exception);
            }

        }
    }

}

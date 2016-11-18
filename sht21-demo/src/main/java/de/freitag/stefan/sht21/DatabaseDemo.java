package de.freitag.stefan.sht21;

import de.freitag.stefan.sht21.data.InvalidJDBCConfigurationException;
import de.freitag.stefan.sht21.data.JDBCConfiguration;
import de.freitag.stefan.sht21.data.SqliteDatastore;
import de.freitag.stefan.sht21.data.model.MeasurementEntity;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.task.MeasurementTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.concurrent.TimeUnit;

public final class DatabaseDemo {

    private static final Logger LOG = LogManager.getLogger(DatabaseDemo.class.getCanonicalName());
    /**
     * The URL pointing to the database.
     */
    @Option(name = "-url", usage = "x", required = true)
    private String url;

    @Option(name = "-driver", usage = "x", required = true)
    private String driver;
    /**
     * Login name used for database authentication.
     */
    @Option(name = "-user", usage = "x", required = true)
    private String username;
    /**
     * Password used for database authentication.
     */
    @Option(name = "-password", usage = "x", required = true)
    private String password;


    /**
     * Application entry point
     *
     * @param args Arguments passed along at the command line.
     */
    public static void main(final String[] args) {
        final DatabaseDemo instance = new DatabaseDemo();
        instance.doMain(args);
        System.exit(0);
    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(DatabaseDemo.class.getCanonicalName());
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

        JDBCConfiguration configuration = null;
        try {
            configuration = new JDBCConfiguration(url, driver, username, password);
        } catch (final InvalidJDBCConfigurationException exception) {
            LOG.error(exception.getMessage(), exception);
        }
        SqliteDatastore store = new SqliteDatastore(configuration);
        final MeasurementTask task = new MeasurementTask(5_000L, MeasureType.TEMPERATURE);
        task.addListener(measurement -> {
            getLogger().info("Sending measurement:" + measurement);

            final MeasurementEntity entity = new MeasurementEntity();
            entity.setType(measurement.getType());
            entity.setCreatedAt(measurement.getCreatedAt());
            entity.setValue(entity.getValue());
            store.insert(entity);
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

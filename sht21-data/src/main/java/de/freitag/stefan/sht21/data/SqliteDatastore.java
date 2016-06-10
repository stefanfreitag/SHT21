package de.freitag.stefan.sht21.data;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by stefan on 30.05.16.
 */
public final class SqliteDatastore implements Datastore {

    /**
     * The name of the table storing the temperature measurements.
     */
    private static final String temperatureTable = "temperature";
    /**
     * The name of the table storing the humidity measurements.
     */
    private static final String humidityTable = "humidity";

    /**
     * Contains information required for connecting to the database.
     */
    private final JDBCConfiguration configuration;
    private Connection connection;


    public SqliteDatastore() throws InvalidJDBCConfigurationException {
        this.configuration = createJDBCConfiguration();

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(this.configuration.getUrl());
            this.createDatabase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(SqliteDatastore.class.getCanonicalName());
    }

    private JDBCConfiguration createJDBCConfiguration() throws InvalidJDBCConfigurationException {
        final Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        } catch (final IOException exception) {
            getLogger().error(exception.getMessage(), exception);
            throw new InvalidJDBCConfigurationException("Unable to load properties file");
        }
        final String url = properties.getProperty("jdbc.url");
        final String driver = properties.getProperty("jdbc.driver");
        final String username = properties.getProperty("jdbc.username");
        final String password = properties.getProperty("jdbc.password");

        return new JDBCConfiguration(url, driver, username, password);


    }

    @Override
    public boolean insert(final Measurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException("Measurement is null");
        }
        String table = "";
        if (MeasureType.HUMIDITY.equals(measurement.getType())) {
            table = humidityTable;
        } else if (MeasureType.TEMPERATURE.equals(measurement.getType())) {
            table = temperatureTable;
        }
        final Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("insert into " + table + " values(NULL," + measurement.getValue() + "," + measurement.getValue() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Measurement getLatest() {
        return null;
    }

    /**
     * @param measureType
     * @return
     */
    @Override
    public long size(final MeasureType measureType) {
        if (measureType == null) {
            throw new IllegalArgumentException(MeasureType.class.getSimpleName() + " is null.");
        }


        String table = "";
        if (MeasureType.HUMIDITY.equals(measureType)) {
            table = humidityTable;
        } else if (MeasureType.TEMPERATURE.equals(measureType)) {
            table = temperatureTable;
        }


        final Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("select count(*) from " + table + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void createDatabase() {

        try {

            final Statement statement = this.connection.createStatement();
            //statement.execute("CREATE DATABASE IF NOT EXISTS `" + databaseName+ "`");

            statement.executeUpdate("create table IF NOT EXISTS " +
                    temperatureTable + " (id integer primary key, value real, measuredat integer)");
            statement.executeUpdate("create unique index IF NOT EXISTS IdxTemperature on " + temperatureTable + " (measuredat, value)");

            statement.executeUpdate("create table IF NOT EXISTS " + humidityTable + " (id integer primary key, value real, measuredat integer)");
            statement.executeUpdate("create unique index IF NOT EXISTS IdxTemperature on " + humidityTable + "(measuredat, value)");

        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }
}

package de.freitag.stefan.sht21.data;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by stefan on 30.05.16.
 */
public class SqliteDatastore implements Datastore {


    private final JDBCConfiguration configuration;
    private final String temperatureTable = "temperature";
    private final String humidityTable = "humidity";
    private Connection connection;
    private String databaseName = "Measurements";

    public SqliteDatastore() {
        this.configuration = xxx();

        try {
            Class.forName("org.sqlite.JDBC");

            this.connection = DriverManager.getConnection(this.configuration.getUrl());
            this.createDatabase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JDBCConfiguration xxx() {
        final Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            String url = properties.getProperty("jdbc.url");
            String driver = properties.getProperty("jdbc.driver");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            return new JDBCConfiguration(url, driver, username, password);
        } catch (final IOException exception) {
            exception.printStackTrace();
            return null;
        }

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
                    this.temperatureTable + " (id integer primary key, value real, measuredat integer)");
            statement.executeUpdate("create unique index IF NOT EXISTS IdxTemperature on " + this.temperatureTable + " (measuredat, value)");

            statement.executeUpdate("create table IF NOT EXISTS " + this.humidityTable + " (id integer primary key, value real, measuredat integer)");
            statement.executeUpdate("create unique index IF NOT EXISTS IdxTemperature on " + this.humidityTable + "(measuredat, value)");

        } catch (final SQLException exception) {
            exception.printStackTrace();
        }


    }
}

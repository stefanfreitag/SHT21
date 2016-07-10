package de.freitag.stefan.sht21.data;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * An SQLite3 datastore.
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

    /**
     * Create a new {@link SqliteDatastore}.
     *
     * @param configuration A non-null {@link JDBCConfiguration}.
     */
    public SqliteDatastore(final JDBCConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration is null");
        }
        this.configuration = configuration;

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(this.configuration.getUrl());
            this.createDatabase();
        } catch (final Exception exception) {
            getLogger().error(exception.getMessage(), exception);
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


    @Override
    public boolean insert(final Measurement measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException("Measurement is null");
        }
        final String table = this.getTableNameFromType(measurement);
        final Statement statement;
        try {
            statement = this.connection.createStatement();
            final int result = statement.executeUpdate("insert into " + table + " values(NULL," + measurement.getValue() + "," + measurement.getCreatedAt().getTime() + ")");
            return result == 1;
        } catch (final SQLException exception) {
            getLogger().error(exception.getMessage(), exception);
        }
        return false;
    }

    private String getTableNameFromType(final Measurement measurement) {
        assert measurement != null;
        String table = "";
        if (MeasureType.HUMIDITY.equals(measurement.getType())) {
            table = humidityTable;
        } else if (MeasureType.TEMPERATURE.equals(measurement.getType())) {
            table = temperatureTable;
        }
        return table;
    }

    /**
     * Return the latest {@link Measurement} of the given {@link MeasureType}.
     *
     * @param measureType The non-null {@link MeasureType} to retrieve
     *                    data for.
     * @return Latest {@link Measurement} of the given {@link MeasureType}.
     */
    @Override
    public Measurement getLatest(final MeasureType measureType) {
        if (measureType == null) {
            throw new IllegalArgumentException("MeasureType is null");
        }
        //TODO
        return null;
    }

    @Override
    public List<Measurement> get(final MeasureType measureType, final long start, final long end) {
        if (measureType == null) {
            throw new IllegalArgumentException("MeasureType is null");
        }
        //TODO
        return null;
    }

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
            final ResultSet resultSet = statement.executeQuery("select count(*) as total from " + table + ";");
            return resultSet.getInt("total");
        } catch (final SQLException exception) {
            getLogger().error(exception.getMessage(), exception);
        }
        return -1;
    }

    @Override
    public void clear() {
        final Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.execute("delete from " + humidityTable + ";");
            statement.execute("delete from " + temperatureTable + ";");
        } catch (final SQLException exception) {
            getLogger().error(exception.getMessage(), exception);
        }
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

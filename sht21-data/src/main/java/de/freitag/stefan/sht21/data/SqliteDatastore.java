package de.freitag.stefan.sht21.data;

import de.freitag.stefan.sht21.data.model.MeasurementEntity;
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
    public boolean insert(final MeasurementEntity measurement) {
        if (measurement == null) {
            throw new IllegalArgumentException("Measurement is null");
        }
        final String table = this.getTableNameFromType(measurement);
        final Statement statement;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            final int result = statement.executeUpdate("insert into " + table + " values(NULL," + measurement.getValue() + "," + measurement.getCreatedAt().getTime() + ")");
            return result == 1;
        } catch (final SQLException exception) {
            getLogger().error("Failed to insert " + measurement + ". Reason: " + exception.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (final SQLException exception) {
                // empty method
            }
        }

    }

    private String getTableNameFromType(final MeasurementEntity measurement) {
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
     * @return Latest {@link MeasurementEntity} of the given {@link MeasureType}.
     */
    @Override
    public MeasurementEntity getLatest(final MeasureType measureType) {
        if (measureType == null) {
            throw new IllegalArgumentException("MeasureType is null");
        }

        String table = "";
        if (MeasureType.HUMIDITY.equals(measureType)) {
            table = humidityTable;
        } else if (MeasureType.TEMPERATURE.equals(measureType)) {
            table = temperatureTable;
        }
        Connection connection = null;
        final Statement statement;
        try {
            connection = this.getConnection();
            statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from " + table + " order by measuredAt desc limit 1;");

            if (resultSet.next()) {
                final long id = resultSet.getLong(1);
                final float value = resultSet.getFloat(2);
                final Date measuredAt = resultSet.getDate(3);

                final MeasurementEntity entity = new MeasurementEntity();
                entity.setId(id);
                entity.setValue(value);
                if (MeasureType.TEMPERATURE.equals(measureType)) {
                    entity.setType(MeasureType.TEMPERATURE);
                } else if (MeasureType.HUMIDITY.equals(measureType)) {
                    entity.setType(MeasureType.HUMIDITY);
                }
                entity.setCreatedAt(measuredAt);
                return entity;
            }
            return null;
        } catch (final SQLException exception) {
            getLogger().error(exception.getMessage(), exception);
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (final SQLException exception) {
                // empty method
            }
        }
    }

    @Override
    public List<MeasurementEntity> get(final MeasureType measureType, final long start, final long end) {
        if (measureType == null) {
            throw new IllegalArgumentException("MeasureType is null");
        }

        String table = "";
        if (MeasureType.HUMIDITY.equals(measureType)) {
            table = humidityTable;
        } else if (MeasureType.TEMPERATURE.equals(measureType)) {
            table = temperatureTable;
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
        Connection connection = null;
        final Statement statement;
        try {
            connection = this.getConnection();
            statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("select count(*) as total from " + table + ";");
            return resultSet.getInt("total");
        } catch (final SQLException exception) {
            getLogger().error(exception.getMessage(), exception);
            return -1;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (final SQLException exception) {
                // empty method
            }
        }

    }

    @Override
    public void clear() {
        Connection connection = null;
        final Statement statement;
        try {
            connection = this.getConnection();
            statement = connection.createStatement();
            statement.execute("delete from " + humidityTable + ";");
            statement.execute("delete from " + temperatureTable + ";");
        } catch (final SQLException exception) {
            getLogger().error(exception.getMessage(), exception);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (final SQLException exception) {
                // empty method
            }
        }
    }

    private void createDatabase() {
        Connection connection = null;
        try {
            connection = this.getConnection();
            final Statement statement = connection.createStatement();
            //statement.execute("CREATE DATABASE IF NOT EXISTS `" + databaseName+ "`");

            statement.executeUpdate("create table IF NOT EXISTS " +
                    temperatureTable + " (id integer primary key, value real, measuredat integer)");
            statement.executeUpdate("create unique index IF NOT EXISTS IdxTemperature on " + temperatureTable + " (measuredat, value)");

            statement.executeUpdate("create table IF NOT EXISTS " + humidityTable + " (id integer primary key, value real, measuredat integer)");
            statement.executeUpdate("create unique index IF NOT EXISTS IdxTemperature on " + humidityTable + "(measuredat, value)");

        } catch (final SQLException exception) {
            getLogger().error(exception.getMessage(), exception);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (final SQLException exception) {
                // empty method
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.configuration.getUrl());
    }
}

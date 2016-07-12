package de.freitag.stefan.sht21.data;

import de.freitag.stefan.sht21.data.model.MeasurementEntity;
import de.freitag.stefan.sht21.model.MeasureType;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test class for {@link SqliteDatastore}.
 */
public final class SqliteDatastoreTest {

    @Before
    public void setup() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        datastore.clear();
    }
    @Test(expected = IllegalArgumentException.class)
    public void insertWithNullThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        datastore.insert(null);
    }

    @Test
    public void insertInEmptyDatabaseReturnsTrue() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        final MeasurementEntity measurement = new MeasurementEntity();
        measurement.setValue(11.0f);
        measurement.setType(MeasureType.TEMPERATURE);
        measurement.setCreatedAt(new Date());
        assertTrue(datastore.insert(measurement));
    }

    @Test
    public void insertSameMeasurementTwiceReturnsFalse() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        final MeasurementEntity measurement = new MeasurementEntity();
        measurement.setValue(11.0f);
        measurement.setType(MeasureType.TEMPERATURE);
        measurement.setCreatedAt(new Date());
        assertTrue(datastore.insert(measurement));
        assertFalse(datastore.insert(measurement));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sizeWithNullThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        datastore.size(null);
    }

    @Test
    public void sizeReturnsZeroForFreshDataStore() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        assertEquals(0, datastore.size(MeasureType.HUMIDITY));
    }

    @Test
    public void sizeReturnsOneForDataStoreWithOneEntry() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        final MeasurementEntity measurement = new MeasurementEntity();
        measurement.setValue(11.0f);
        measurement.setType(MeasureType.TEMPERATURE);
        measurement.setCreatedAt(new Date());
        assertTrue(datastore.insert(measurement));
        assertEquals(1, datastore.size(MeasureType.TEMPERATURE));
        assertEquals(0, datastore.size(MeasureType.HUMIDITY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getLatestWithNullMeasureTypeThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        datastore.getLatest(null);
    }

    @Test
    public void getLatestReturnsLatestMeasurement() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        final MeasurementEntity measurement = new MeasurementEntity();
        measurement.setValue(11.0f);
        measurement.setType(MeasureType.TEMPERATURE);
        measurement.setCreatedAt(new Date());
        assertNull(datastore.getLatest(MeasureType.TEMPERATURE));
        assertTrue(datastore.insert(measurement));
        final MeasurementEntity latest = datastore.getLatest(MeasureType.TEMPERATURE);
        assertEquals(measurement.getValue(), latest.getValue(), 0.00001f);
        assertEquals(measurement.getType(), latest.getType());
        assertEquals(measurement.getCreatedAt(), latest.getCreatedAt());
    }

    @Test
    public void getLatestReturnsLatestMeasurementWithMoreThanOneMeasurementInDatabase() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);

        final Date createdAt = new Date();
        final MeasurementEntity measurement1 = new MeasurementEntity();
        measurement1.setValue(11.0f);
        measurement1.setType(MeasureType.TEMPERATURE);
        measurement1.setCreatedAt(createdAt);
        assertTrue(datastore.insert(measurement1));

        final MeasurementEntity measurement2 = new MeasurementEntity();
        measurement2.setValue(22.0f);
        measurement2.setType(MeasureType.TEMPERATURE);
        measurement2.setCreatedAt(new Date(createdAt.getTime() - 100));
        assertTrue(datastore.insert(measurement2));

        final MeasurementEntity latest = datastore.getLatest(MeasureType.TEMPERATURE);
        assertEquals(measurement1.getValue(), latest.getValue(), 0.00001f);
        assertEquals(measurement1.getType(), latest.getType());
        assertEquals(measurement1.getCreatedAt(), latest.getCreatedAt());
    }


    @Test(expected = IllegalArgumentException.class)
    public void getWithNullMeasureTypeThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        datastore.get(null, 0L, 1L);
    }
}
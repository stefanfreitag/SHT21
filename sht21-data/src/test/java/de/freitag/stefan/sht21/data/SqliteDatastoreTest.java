package de.freitag.stefan.sht21.data;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        final Measurement measurement = Measurement.create(11.0f, MeasureType.TEMPERATURE);

        assertTrue(datastore.insert(measurement));
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
        final Measurement measurement = Measurement.create(11.0f, MeasureType.TEMPERATURE);
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


    @Test(expected = IllegalArgumentException.class)
    public void getWithNullMeasureTypeThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        datastore.get(null, 0L, 1L);
    }
}
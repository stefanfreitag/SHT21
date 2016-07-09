package de.freitag.stefan.sht21.data;

import de.freitag.stefan.sht21.model.MeasureType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link SqliteDatastore}.
 */
public final class SqliteDatastoreTest {

    @Test(expected = IllegalArgumentException.class)
    public void insertWithNullThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final JDBCConfiguration configuration = JDBCConfiguration.fromProperties();
        final SqliteDatastore datastore = new SqliteDatastore(configuration);
        datastore.insert(null);
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
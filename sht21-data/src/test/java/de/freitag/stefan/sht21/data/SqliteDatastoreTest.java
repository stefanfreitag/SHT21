package de.freitag.stefan.sht21.data;

import org.junit.Test;

/**
 * Created by stefan on 30.05.16.
 */
public class SqliteDatastoreTest {

    @Test
    public void test() {
        new SqliteDatastore();
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertWithNullThrowsIllegalArgumentException() {
        final SqliteDatastore datastore = new SqliteDatastore();
        datastore.insert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sizeWithNullThrowsIllegalArgumentException() {
        final SqliteDatastore datastore = new SqliteDatastore();
        datastore.size(null);
    }
}
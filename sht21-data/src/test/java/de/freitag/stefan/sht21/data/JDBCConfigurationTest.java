package de.freitag.stefan.sht21.data;

import org.junit.Test;

/**
 * Test class for {@link JDBCConfiguration}
 */
public final class JDBCConfigurationTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullUrlThrowsIllegalArgumentException() {
        final String url = null;
        final String driver = "driver";
        final String username = "username";
        final String password = "password";
        new JDBCConfiguration(url, driver, username, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullUsernameThrowsIllegalArgumentException() {
        final String url = "url";
        final String driver = "driver";
        final String username = null;
        final String password = "password";
        new JDBCConfiguration(url, driver, username, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyUsernameThrowsIllegalArgumentException() {
        final String url = "url";
        final String driver = "driver";
        final String username = "";
        final String password = "password";
        new JDBCConfiguration(url, driver, username, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullPasswordThrowsIllegalArgumentException() {
        final String url = "url";
        final String driver = "driver";
        final String username = "user";
        final String password = null;
        new JDBCConfiguration(url, driver, username, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyPasswordThrowsIllegalArgumentException() {
        final String url = "url";
        final String driver = "driver";
        final String username = "user";
        final String password = "";
        new JDBCConfiguration(url, driver, username, password);
    }
}
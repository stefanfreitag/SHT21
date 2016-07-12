package de.freitag.stefan.sht21.data;

import org.junit.Test;

/**
 * Test class for {@link JDBCConfiguration}
 */
public final class JDBCConfigurationTest {

    @Test(expected = InvalidJDBCConfigurationException.class)
    public void constructorWithNullUrlThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final String url = null;
        final String driver = "driver";
        final String username = "username";
        final String password = "password";
        new JDBCConfiguration(url, driver, username, password);
    }

    @Test(expected = InvalidJDBCConfigurationException.class)
    public void constructorWithNullUsernameThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final String url = "url";
        final String driver = "driver";
        final String username = null;
        final String password = "password";
        new JDBCConfiguration(url, driver, username, password);
    }

    @Test(expected = InvalidJDBCConfigurationException.class)
    public void constructorWithEmptyUsernameThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final String url = "url";
        final String driver = "driver";
        final String username = "";
        final String password = "password";
        new JDBCConfiguration(url, driver, username, password);
    }

    @Test(expected = InvalidJDBCConfigurationException.class)
    public void constructorWithNullPasswordThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final String url = "url";
        final String driver = "driver";
        final String username = "user";
        final String password = null;
        new JDBCConfiguration(url, driver, username, password);
    }

    @Test(expected = InvalidJDBCConfigurationException.class)
    public void constructorWithEmptyPasswordThrowsIllegalArgumentException() throws InvalidJDBCConfigurationException {
        final String url = "url";
        final String driver = "driver";
        final String username = "user";
        final String password = "";
        new JDBCConfiguration(url, driver, username, password);
    }
}

package de.freitag.stefan.sht21.data;


import java.util.Objects;

public final class JDBCConfiguration {
    /**
     * The URL pointing to the database.
     */
    private final String url;
    private final String driver;
    /**
     * Login name used for database authentication.
     */
    private final String username;
    /**
     * Password used for database authentication.
     */
    private final String password;

    /**
     * Create a new  {@link JDBCConfiguration}
     *
     * @param url      The non-null URL pointing to the database.
     * @param driver
     * @param username Non-null and non-empty login name used for database authentication.
     * @param password Non-null and non-empty password used for database authentication.
     */
    public JDBCConfiguration(final String url, final String driver, final String username, final String password) throws InvalidJDBCConfigurationException {
        if (url == null) {
            throw new InvalidJDBCConfigurationException("URL is null");
        }
        if (username == null) {
            throw new InvalidJDBCConfigurationException("User name is null");
        }
        if (username.isEmpty()) {
            throw new InvalidJDBCConfigurationException("User name is empty");
        }

        if (password == null) {
            throw new InvalidJDBCConfigurationException("Password is null");
        }
        if (password.isEmpty()) {
            throw new InvalidJDBCConfigurationException("Password is empty");
        }
        this.url = url;
        this.driver = driver;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDriver() {
        return this.driver;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JDBCConfiguration that = (JDBCConfiguration) o;
        return Objects.equals(getUrl(), that.getUrl()) &&
                Objects.equals(getDriver(), that.getDriver()) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getDriver(), getUsername(), getPassword());
    }

    @Override
    public String toString() {
        return "JDBCConfiguration{" +
                "driver='" + driver + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

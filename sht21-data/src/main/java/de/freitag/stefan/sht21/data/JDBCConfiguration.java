
package de.freitag.stefan.sht21.data;


import java.util.Objects;

public final class JDBCConfiguration {

    private final String url;
    private final String driver;
    private final String username;
    private final String password;

    public JDBCConfiguration(final String url, final String driver, final String username, final String password) {
        this.url = url;
        this.driver = driver;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

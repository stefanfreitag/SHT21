package de.freitag.stefan.sht21.data;

public final class InvalidJDBCConfigurationException extends Exception {
    /**
     * Create a new {@link InvalidJDBCConfigurationException}.
     *
     * @param message The error message.
     */
    public InvalidJDBCConfigurationException(final String message) {
        super(message);
    }

    /**
     * Create a new {@link InvalidJDBCConfigurationException}.
     *
     * @param throwable The cause of the exception.
     */
    public InvalidJDBCConfigurationException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Create a new {@link InvalidJDBCConfigurationException}.
     *
     * @param message   The error message
     * @param throwable The cause of the exception.
     */
    public InvalidJDBCConfigurationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}


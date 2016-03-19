package de.freitag.stefan.sht21.model;

/**
 *
 */
public final class UnsupportedMeasureTypeException extends Exception {

    /**
     * Create a new {@link UnsupportedMeasureTypeException}.
     *
     * @param message
     */
    public UnsupportedMeasureTypeException(final String message) {
        super(message);
    }

    /**
     * Create a new {@link UnsupportedMeasureTypeException}.
     *
     * @param throwable
     */
    public UnsupportedMeasureTypeException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Create a new {@link UnsupportedMeasureTypeException}.
     *
     * @param message
     * @param throwable
     */
    public UnsupportedMeasureTypeException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}

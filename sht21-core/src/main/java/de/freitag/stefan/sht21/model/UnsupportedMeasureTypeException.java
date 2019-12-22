package de.freitag.stefan.sht21.model;

/**
 * This exception should be used if an unsupported {@link de.freitag.stefan.sht21.model.MeasureType}
 * was specified.
 */
public final class UnsupportedMeasureTypeException extends Exception {

  /**
   * Create a new {@link UnsupportedMeasureTypeException}.
   *
   * @param message The error message.
   */
  public UnsupportedMeasureTypeException(final String message) {
    super(message);
  }

  /**
   * Create a new {@link UnsupportedMeasureTypeException}.
   *
   * @param throwable The cause of the exception.
   */
  public UnsupportedMeasureTypeException(final Throwable throwable) {
    super(throwable);
  }

  /**
   * Create a new {@link UnsupportedMeasureTypeException}.
   *
   * @param message The error message
   * @param throwable The cause of the exception.
   */
  public UnsupportedMeasureTypeException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}

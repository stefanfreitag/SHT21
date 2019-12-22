package de.freitag.stefan.sht21;

import com.pi4j.io.i2c.I2CBus;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import de.freitag.stefan.sht21.model.UnsupportedMeasureTypeException;
import de.freitag.stefan.sht21.task.MeasurementTask;
import de.freitag.stefan.sht21.task.MeasurementTaskListener;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

@Log4j2
public final class SHT21Demo implements MeasurementTaskListener {

  /** Device address. */
  private static final int I2C_ADDRESS = 0x40;

  @Option(name = "-type", usage = "Allowed values: humidity, temperature", required = true)
  private String type;

  @Option(name = "-implementation", usage = "Allowed values: dummy, real", required = true)
  private String implementation;

  /**
   * Application entry point
   *
   * @param args Arguments passed along at the command line.
   */
  public static void main(final String[] args) {
    final SHT21Demo instance = new SHT21Demo();
    instance.doMain(args);
    System.exit(0);
  }

  private void doMain(final String[] args) {
    final CmdLineParser parser = new CmdLineParser(this);
    try {
      parser.parseArgument(args);
    } catch (CmdLineException exception) {
      System.err.println(exception.getMessage());
      parser.printUsage(System.err);
      return;
    }
    try {
      execute(type, implementation);
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(5000L);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    } catch (final UnsupportedMeasureTypeException exception) {
      log.error(exception.getMessage(), exception);
    }
  }

  private void execute(@NonNull String type, @NonNull String implementation)
      throws UnsupportedMeasureTypeException {
    MeasureType measureType;
    if ("humidity".equalsIgnoreCase(type)) {
      measureType = MeasureType.HUMIDITY;

    } else if ("temperature".equalsIgnoreCase(type)) {
      measureType = MeasureType.TEMPERATURE;
    } else {
      throw new UnsupportedMeasureTypeException("Measure type '" + type + "' is not supported.");
    }
    SHT21 sht21;

    if ("dummy".equalsIgnoreCase(implementation)) {
      sht21 = new SHT21DummyImpl();

    } else if ("real".equalsIgnoreCase(implementation)) {
      sht21 = SHT21Impl.create(I2CBus.BUS_1, I2C_ADDRESS);
    } else {
      throw new RuntimeException("Unsupported implementation: " + implementation);
    }

    final MeasurementTask task =
        MeasurementTask.builder().sht21(sht21).measureType(measureType).interval(5_000L).build();
    task.addListener(this);
    task.start();
  }

  /**
   * Called whenever a new measurement has been received from the sensor.
   *
   * @param measurement
   */
  @Override
  public void onReceived(@NonNull Measurement measurement) {
    log.info(measurement);
  }
}

package de.freitag.stefan.sht21;

import static com.mashape.unirest.http.Unirest.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pi4j.io.i2c.I2CBus;
import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;
import de.freitag.stefan.sht21.model.UnsupportedMeasureTypeException;
import de.freitag.stefan.sht21.task.MeasurementTask;
import de.freitag.stefan.sht21.task.MeasurementTaskListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class SHT21Demo implements MeasurementTaskListener {

  /** Device address. */
  private static final int I2C_ADDRESS = 0x40;

  private String appConfigPath = Paths.get("app.properties").toAbsolutePath().toString();

  private Properties appProps = new Properties();

  private Configuration configuration;

  public SHT21Demo() {
    this.configuration = loadProperties();
  }

  private Configuration loadProperties() {
    try {
      appProps.load(new FileInputStream(appConfigPath));
      String endpoint = appProps.getProperty("sht21.endpoint");
      log.info("Found endpoint in configuration: " + endpoint);
      long interval = Long.valueOf(appProps.getProperty("sht21.measurement.interval"));
      log.info("Found interval in seconds: " + interval);

      String type = appProps.getProperty("sht21.measurement.type");
      Optional<MeasureType> ifPresent = Enums.getIfPresent(MeasureType.class, type);

      String implementation = appProps.getProperty("sht21.implementation");
      Optional<SensorType> ifPresent1 = Enums.getIfPresent(SensorType.class, implementation);

      String uuid = appProps.getProperty("sht21.uuid");
      UUID uuid1 = UUID.fromString(uuid);
      return Configuration.builder()
          .endpoint(endpoint)
          .interval(interval)
          .measureType(ifPresent.get())
          .implementation(ifPresent1.get())
          .uuid(uuid1)
          .build();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException("Error creating configuration");
    }
  }

  /**
   * Application entry point
   *
   * @param args Arguments passed along at the command line.
   */
  public static void main(final String[] args) {
    final SHT21Demo instance = new SHT21Demo();

    com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper =
        new com.fasterxml.jackson.databind.ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    ObjectMapper objectMapper =
        new ObjectMapper() {

          public <T> T readValue(String value, Class<T> valueType) {
            try {
              return jacksonObjectMapper.readValue(value, valueType);
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }

          public String writeValue(Object value) {
            try {
              return jacksonObjectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
              throw new RuntimeException(e);
            }
          }
        };
    Unirest.setObjectMapper(objectMapper);

    instance.doMain(args);
    System.exit(0);
  }

  private void doMain(final String[] args) {
    try {

      execute();
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

  private void execute() throws UnsupportedMeasureTypeException {
    MeasureType measureType;
    if (MeasureType.HUMIDITY.equals(configuration.getMeasureType())) {
      measureType = MeasureType.HUMIDITY;

    } else if (MeasureType.TEMPERATURE.equals(configuration.getMeasureType())) {
      measureType = MeasureType.TEMPERATURE;
    } else {
      throw new UnsupportedMeasureTypeException(
          "Measure type '" + configuration.getMeasureType() + "' is not supported.");
    }
    SHT21 sht21;

    if (SensorType.DUMMY.equals(configuration.getImplementation())) {
      sht21 = new SHT21DummyImpl();

    } else if (SensorType.REAL.equals(configuration.getImplementation())) {
      sht21 = SHT21Impl.create(I2CBus.BUS_1, I2C_ADDRESS);
    } else {
      throw new RuntimeException(
          "Unsupported implementation: " + configuration.getImplementation());
    }

    final MeasurementTask task =
        MeasurementTask.builder()
            .sht21(sht21)
            .measureType(measureType)
            .interval(TimeUnit.SECONDS.toMillis(configuration.getInterval()))
            .build();
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
    try {
      String fullEndpoint =
          configuration.getEndpoint()
              + "/sensors/"
              + configuration.getUuid()
              + "/"
              + "measurements/";

      HttpResponse<JsonNode> httpResponse =
          post(fullEndpoint).header("Content-Type", "application/json").body(measurement).asJson();
      log.info("Storing measurement" + measurement);

    } catch (final UnirestException exception) {
      log.error(exception.getMessage());
    }
  }
}

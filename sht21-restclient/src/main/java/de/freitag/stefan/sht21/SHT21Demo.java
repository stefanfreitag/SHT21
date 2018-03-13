package de.freitag.stefan.sht21;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.mashape.unirest.http.Unirest.post;

@Log4j2
public final class SHT21Demo implements MeasurementTaskListener {

    /**
     * Device address.
     */
    private static final int I2C_ADDRESS = 0x40;

    private String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    private String appConfigPath = rootPath + "app.properties";

    private String endpoint;
    // Measurement interval in seconds.
    private long interval;

    private String type;

    private String implementation;


    private String uuid;


    private Properties appProps = new Properties();




    public SHT21Demo() {
        loadProperties();
    }

    private void loadProperties() {
        try {
            appProps.load(new FileInputStream(appConfigPath));
            endpoint = appProps.getProperty("sht21.endpoint");
            log.info("Found endpoint in configuration: " + endpoint);
            interval = Long.valueOf(appProps.getProperty("sht21.measurement.interval"));
            log.info("Found interval in seconds: " + interval);

            type = appProps.getProperty("sht21.measurement.type");
            implementation = appProps.getProperty("sht21.implementation");
            uuid=appProps.getProperty("sht21.uuid");
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }





    /**
     * Application entry point
     *
     * @param args Arguments passed along at the command line.
     */
    public static void main(final String[] args) {
        final SHT21Demo instance = new SHT21Demo();

        com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                = new com.fasterxml.jackson.databind.ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


        ObjectMapper objectMapper = new ObjectMapper() {

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

    private void execute(@NonNull String type, @NonNull String implementation) throws UnsupportedMeasureTypeException {
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

        final MeasurementTask task = MeasurementTask.builder().sht21(sht21).measureType(measureType)
                .interval(TimeUnit.SECONDS.toMillis(interval)).build();
        task.addListener(this);
        task.start();
    }

    /**
     * Called whenever a new measurement has been
     * received from the sensor.
     *
     * @param measurement
     */
    @Override
    public void onReceived(@NonNull Measurement measurement) {
        try {
            String fullEndpoint = endpoint + "/sensors/" + uuid + "/" + "measurements/";

            HttpResponse<JsonNode> httpResponse = post(fullEndpoint)
                    .header("Content-Type", "application/json")
                    .body(measurement).asJson();
            log.info("Storing measurement" + measurement);

        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
        }


    }


}

class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
        arg1.writeString(arg0.toString());
    }
}

class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(arg0.getText());
    }
}
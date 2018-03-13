package de.freitag.stefan.spring.sht21.server.api.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by stefan on 17.06.17.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SensorNotFoundException extends RuntimeException {

    public SensorNotFoundException(String uuid) {
        super("Could not find a sensor with uuid '" + uuid + "'.");
    }
}

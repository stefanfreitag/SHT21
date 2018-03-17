package de.freitag.stefan.spring.sht21.server.api.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by stefan on 17.06.17.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class SensorUuidAlreadyExistsException extends RuntimeException {

    public SensorUuidAlreadyExistsException(String uuid) {
        super("The uuid '" + uuid + "' is already registered.");
    }
}

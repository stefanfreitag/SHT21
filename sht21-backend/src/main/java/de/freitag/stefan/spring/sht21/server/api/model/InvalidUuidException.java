package de.freitag.stefan.spring.sht21.server.api.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUuidException extends RuntimeException {
    public InvalidUuidException(String uuid) {
        super("The value '" + uuid + "' is not a valid UUID.");
    }
}

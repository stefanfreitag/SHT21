package de.freitag.stefan.spring.sht21.server.service;

import de.freitag.stefan.spring.sht21.server.api.model.Measurement;
import de.freitag.stefan.spring.sht21.server.api.model.Sensor;

import java.util.List;

public interface SensorService {

    List<Sensor> readAll();

    Sensor readByUuid(String uuid);

    Sensor create(String uuid, String description);

    boolean exists(String uuid);

    List<Measurement> getMeasurements(String uuid);

    List<Measurement> getMeasurements(String uuid, Long from, Long to);

    Measurement addMeasurement(String uuid, Measurement measurement);

}

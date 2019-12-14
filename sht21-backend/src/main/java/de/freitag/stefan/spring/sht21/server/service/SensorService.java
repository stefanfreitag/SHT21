package de.freitag.stefan.spring.sht21.server.service;

import de.freitag.stefan.spring.sht21.server.api.model.MeasurementDTO;
import de.freitag.stefan.spring.sht21.server.api.model.SensorDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SensorService {

    List<SensorDTO> readAll();

    Optional<SensorDTO> readByUuid(String uuid);

    /**
     * Create a new sensorDTO.
     * @param sensorDTO The SensorDTO DTO.
     * @return
     */
    SensorDTO create(SensorDTO sensorDTO);

    /**
     * Update the information for an existing sensor.
     * @param uuid
     * @param name
     * @param description
     * @return
     */
    SensorDTO update(String uuid, String name, String description);

    boolean exists(String uuid);

    List<MeasurementDTO> getMeasurements(String uuid);

    List<MeasurementDTO> getMeasurements(String uuid, Long from, Long to);

    MeasurementDTO addMeasurement(String uuid, MeasurementDTO measurementDTO);

    /**
     * Delete an existing sensor by specifying its unique identifier.
     * @param uuid The unique identifier of the sensor to delete.
     * @return
     * @throws UuidNotFoundException if the uuid to delete could not be found.
     */
    ResponseEntity<Void> delete(String uuid) throws UuidNotFoundException;
}

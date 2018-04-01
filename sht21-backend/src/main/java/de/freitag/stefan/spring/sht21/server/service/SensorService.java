package de.freitag.stefan.spring.sht21.server.service;

import de.freitag.stefan.spring.sht21.server.api.model.MeasurementDTO;
import de.freitag.stefan.spring.sht21.server.api.model.SensorDTO;

import java.util.List;

public interface SensorService {

    List<SensorDTO> readAll();

    SensorDTO readByUuid(String uuid);

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

}

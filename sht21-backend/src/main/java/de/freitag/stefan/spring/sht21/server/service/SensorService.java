package de.freitag.stefan.spring.sht21.server.service;

import de.freitag.stefan.spring.sht21.server.api.model.SensorDTO;
import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
import de.freitag.stefan.spring.sht21.server.domain.repositories.SensorRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SensorService {

  /** Stores basic information about sensors. */
  private final SensorRepository repository;

  /** Used to store measurements related to the sensors. */
  private InfluxService influxService;

  @Autowired
  public SensorService(
      @NonNull final InfluxService influxService, @NonNull final SensorRepository repository) {
    this.repository = repository;
    this.influxService = influxService;
  }

  public List<Sensor> readAll() {
    return new ArrayList<>(this.repository.findAll());
  }

  public Sensor create(@NonNull final Sensor sensor) {
    Optional<Sensor> result = this.repository.findByUuid(sensor.getUuid());
    if (result.isPresent()) {
      return result.get();
    } else {
      this.repository.save(sensor);
      log.info(String.format("Sensor %s has been created.", sensor.getName()));
      return sensor;
    }
  }

  public SensorDTO update(final String uuid, final String name, final String description) {
    Optional<Sensor> sensor = this.repository.findByUuid(uuid);
    sensor.get().setName(name);
    sensor.get().setDescription(description);
    // TODO
    // Sensor save = this.repository.save(sensor);
    //        return this.convertToDto(sensor.get());
    return null;
  }

  public Optional<Sensor> readByUuid(@NonNull final String uuid) {
    log.info("Finding sensor with uuid " + uuid);
    return this.repository.findByUuid(uuid);
  }

  public boolean exists(@NonNull final UUID uuid) {
    return this.repository.existsByUuid(uuid.toString());
  }

  public List<Measurement> getMeasurements(final String uuid) {
    return this.influxService.getMeasurements(uuid);
  }

  public List<Measurement> getMeasurements(
      @NonNull final String uuid, final Instant from, final Instant to) {

    log.info("Getting measurements for sensor with uuid " + uuid + " from " + from + " to " + to);
    return this.influxService.getMeasurements(uuid, from, to);
  }

  public Measurement addMeasurement(
      @NonNull final String uuid, @NonNull final Measurement measurement) {

    // Optional<Sensor> byUuid = this.repository.findByUuid(uuid);
    // entity.setSensor(byUuid.get());
    // TODO
    log.info("Writing new measurement for sensor " + uuid + ": " + measurement);
    influxService.writeMeasurement(uuid, measurement);
    //     this.measurementRepository.save(entity);
    // byUuid.add(entity);
    // this.repository.save(byUuid.get());
    //  measurementRepository.save(entity);
    return measurement;
  }

  public ResponseEntity<Void> delete(final String uuid) throws UuidNotFoundException {
    Optional<Sensor> sensor = this.repository.findByUuid(uuid);
    if (!sensor.isPresent()) {
      throw new UuidNotFoundException("Could not find sensor with uuid " + uuid);
    }

    this.repository.delete(sensor.get());
    log.info("Deleted sensor with uuid " + uuid);
    return null;
  }
}

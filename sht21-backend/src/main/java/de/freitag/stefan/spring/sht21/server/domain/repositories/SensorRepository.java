package de.freitag.stefan.spring.sht21.server.domain.repositories;

import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Long> {
    Sensor findByUuid(String uuid);

}

package de.freitag.stefan.spring.sht21.server.domain.repositories;

import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Long> {
  Optional<Sensor> findByUuid(@NonNull final String uuid);
}

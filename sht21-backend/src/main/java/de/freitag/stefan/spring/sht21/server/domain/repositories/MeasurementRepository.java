package de.freitag.stefan.spring.sht21.server.domain.repositories;

import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Long> {

    @Query(value = "SELECT * FROM MEASUREMENT WHERE sensor_id=:id AND measuredat>=:start AND measuredat<=:end", nativeQuery = true)
    List<Measurement> findByMeasuredAtBetweenAndSensor_Id(@Param("start") long start, @Param("end") long end, @Param("id") String id);
}

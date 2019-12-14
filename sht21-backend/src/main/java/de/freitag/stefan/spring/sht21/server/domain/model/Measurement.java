package de.freitag.stefan.spring.sht21.server.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;


@NoArgsConstructor
@Data
@EqualsAndHashCode
@RedisHash("Measurement")
public class Measurement implements Serializable {
    private Long id;

    @JsonIgnore
    @CreatedDate
    private Long createdAt;

    private String sensorId;

    private Long measuredAt;

    private double value;

    private String unit;

    void createdAt() {
        this.createdAt = Instant.now().toEpochMilli();
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", measuredAt=" + measuredAt +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}

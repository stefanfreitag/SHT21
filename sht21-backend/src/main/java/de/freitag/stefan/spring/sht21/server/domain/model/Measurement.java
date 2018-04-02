package de.freitag.stefan.spring.sht21.server.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@Data
@EqualsAndHashCode
@Entity
public class Measurement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @CreatedDate
    @Column(name = "createdat", nullable = false)
    private Long createdAt;


    @Column(name = "measuredat", nullable = false)
    private Long measuredAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "uuid")
    private Sensor sensor;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "unit", nullable = false)
    private String unit;

    public Measurement() {
        //empty default constructor
    }

    @PrePersist
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

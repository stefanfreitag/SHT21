package de.freitag.stefan.spring.sht21.server.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Sensor implements Serializable{
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;

    public Sensor() {
        this.measurements = new ArrayList<>();
    }

    public void add(Measurement measurement){
        this.measurements.add(measurement);
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

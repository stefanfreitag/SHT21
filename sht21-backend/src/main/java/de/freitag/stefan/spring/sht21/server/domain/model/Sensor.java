package de.freitag.stefan.spring.sht21.server.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "measurements")
@Entity
public class Sensor implements Serializable{
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "sensor", orphanRemoval = true)
    private List<Measurement> measurements;

    public Sensor() {
        this.measurements = new ArrayList<>();
    }

    public void add(Measurement measurement){
        this.measurements.add(measurement);
    }

}

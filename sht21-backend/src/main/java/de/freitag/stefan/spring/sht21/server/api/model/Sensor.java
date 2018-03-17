package de.freitag.stefan.spring.sht21.server.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@Data
public class Sensor {
    @ApiModelProperty(notes = "Unique identifier.")
    @JsonProperty("uuid")
    private String uuid;


    @ApiModelProperty(notes = "Name of the sensor.")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Semantic description for the sensor.")
    @JsonProperty("description")
    private String description;
}

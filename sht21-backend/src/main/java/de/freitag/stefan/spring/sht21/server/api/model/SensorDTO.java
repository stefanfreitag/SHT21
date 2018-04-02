package de.freitag.stefan.spring.sht21.server.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SensorDTO {
    @ApiModelProperty(notes = "Unique identifier. Required format is UUID version 4.", example = "3c6bf1df-21bd-4dd9-bceb-34d2c0e1a900")
    @JsonProperty("uuid")
    private String uuid;


    @ApiModelProperty(notes = "Name of the sensor.", example = "Sensor SHT21")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Semantic description for the sensor.", example = "On table in living room.")
    @JsonProperty("description")
    private String description;
}

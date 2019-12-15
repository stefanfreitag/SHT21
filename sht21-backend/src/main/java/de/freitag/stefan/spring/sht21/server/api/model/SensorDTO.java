package de.freitag.stefan.spring.sht21.server.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SensorDTO {
    @Schema(description = "Unique identifier. Required format is UUID version 4.", example = "3c6bf1df-21bd-4dd9-bceb-34d2c0e1a900")
    @Size(min = 36, max = 36)
    @JsonProperty("uuid")
    private String uuid;


    @Schema(description = "Name of the sensor.", example = "Sensor SHT21")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Semantic description for the sensor.", example = "On table in living room.")
    @JsonProperty("description")
    private String description;
}

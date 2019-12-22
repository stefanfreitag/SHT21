package de.freitag.stefan.spring.sht21.server.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SensorDTO {
  @Schema(
      description = "Unique identifier. Required format is UUID version 4.",
      example = "3c6bf1df-21bd-4dd9-bceb-34d2c0e1a900")
  @Size(min = 36, max = 36)
  @JsonProperty("uuid")
  private String uuid;

  @Schema(description = "Name of the sensor.", example = "Sensor SHT21")
  @JsonProperty("name")
  private String name;

  @Schema(
      description = "Semantic description for the sensor.",
      example = "On table in living room.")
  @JsonProperty("description")
  private String description;

  public static SensorDTO from(@NonNull Sensor sensor) {
    return SensorDTO.builder()
        .uuid(sensor.getUuid())
        .name(sensor.getName())
        .description(sensor.getDescription())
        .build();
  }
}

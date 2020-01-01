package de.freitag.stefan.spring.sht21.server.domain.model;

import java.io.Serializable;
import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Sensor implements Serializable {

  @Id private String uuid;

  private String name;

  private String description;
}

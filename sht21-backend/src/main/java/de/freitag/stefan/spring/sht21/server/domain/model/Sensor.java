package de.freitag.stefan.spring.sht21.server.domain.model;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Setter
@Getter
@RedisHash("Sensor")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Sensor implements Serializable{

    @Id
    @Indexed
    private String uuid;

    private String name;

    private String description;

}

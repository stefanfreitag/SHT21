package de.freitag.stefan.spring.sht21.server.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Measurement {
    @ApiModelProperty(notes = "Measurement unit.")
    private String unit;

    @ApiModelProperty(notes = "Measured value.")
    private BigDecimal value;

    @ApiModelProperty(notes = "Timestamp of measurement in milliseconds since epoch.")
    private long measuredAt;

}


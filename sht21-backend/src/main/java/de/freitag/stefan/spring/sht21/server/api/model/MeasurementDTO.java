package de.freitag.stefan.spring.sht21.server.api.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
//@ApiModel
public class MeasurementDTO {
    //    @ApiModelProperty(notes = "Measurement unit.")
    private String unit;

    // @ApiModelProperty(notes = "Measured value.")
    private BigDecimal value;

    //@ApiModelProperty(notes = "Timestamp of measurement in milliseconds since epoch.")
    private long measuredAt;

}


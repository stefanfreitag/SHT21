package de.freitag.stefan.spring.sht21.server.api;

import de.freitag.stefan.spring.sht21.server.api.model.*;
import de.freitag.stefan.spring.sht21.server.service.SensorService;
import de.freitag.stefan.spring.sht21.server.service.UuidNotFoundException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@OpenAPIDefinition
@Tag(name = "sensor", description = "The sensor API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@Api(description = "Operations related to sensors and measurements.")
@RestController
public class SensorsApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SensorService service;

    @Autowired
    public SensorsApiController(final SensorService service) {
        this.service = service;
    }

    @Operation(summary = "Read the list of sensors", description = "Returns the list sensors", tags = {"sensor"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SensorDTO.class)))

            )
    }
    )
    @RequestMapping(value = "/sensors",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public List<SensorDTO> readSensors() {
        return this.service.readAll();
    }

    @Operation(summary = "Read all measurements for a single sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved measurements")
    }
    )
    @RequestMapping(value = "/sensors/{id}/measurements",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public List<MeasurementDTO> sensorsIdMeasurementsGet(@PathVariable("id") String id,
                                                         @RequestParam(value = "from", required = false) Long from,
                                                         @RequestParam(value = "to", required = false) Long to) {
        if (!Sensors.isValidUuid(id)) {
            throw new InvalidUuidException(id);
        }

/*        SensorDTO sensorDTO = this.service.readByUuid(id).get();
        if (sensorDTO == null) {
            throw new SensorNotFoundException(id);
        }
*/
        //TODO
        if (from == null || to == null) {
            return this.service.getMeasurements(id);
        } else
            return this.service.getMeasurements(id, from, to);

    }

    @Operation(summary = "Create a new sensor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The sensor was created successfully."),
            @ApiResponse(responseCode = "400", description = "There is a problem with the provided information. E.g. the UUID is null."),
            @ApiResponse(responseCode = "409", description = "There exits already a sensor with the given UUID.")
    }
    )
    @RequestMapping(value = "/sensors",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public SensorDTO createSensor(@Valid @RequestBody SensorDTO body) {
        if (!Sensors.isValidUuid(body.getUuid())) {
            throw new InvalidUuidException(body.getUuid());
        }
        if (this.service.exists(body.getUuid())) {
            throw new SensorUuidAlreadyExistsException(body.getUuid());
        }
        return this.service.create(body);
    }

    @Operation(summary = "Delete a sensor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The sensor was deleted successfully."),
            @ApiResponse(responseCode = "404", description = "The sensor could not be found."),
    }
    )
    @RequestMapping(value = "/sensors/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> sensorsUuidDelete(@PathVariable(name = "id") String uuid) {
        try {
            return this.service.delete(uuid);
        } catch (UuidNotFoundException exception) {
            throw new SensorNotFoundException(uuid);
        }
    }


    @Operation(summary = "Read information for a single sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved sensor")
    }
    )
    @RequestMapping(value = "/sensors/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public SensorDTO readSensorByUuid(@PathVariable(name = "id") String uuid) {
        SensorDTO sensorDTO = this.service.readByUuid(uuid).get();
        if (sensorDTO != null) {
            return sensorDTO;
        } else {
            throw new SensorNotFoundException(uuid);
        }
    }

    @Operation(summary = "Update the sensor information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated sensor")
    }
    )
    @RequestMapping(value = "/sensors/{id}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public SensorDTO updateSensorByUuid(@PathVariable(name = "id") final String uuid, @RequestBody SensorDTO body) {

        if (!uuid.equalsIgnoreCase(body.getUuid())) {
            throw new ApiException("Not allowed to update sensorDTO " + uuid + " with this information. Wrong uuid: " + body.getUuid());
        }

        if (body.getName() == null) {
            throw new ApiException("Update sensorDTO with null name is not allowed.");
        }

        SensorDTO sensorDTO = this.service.readByUuid(uuid).get();
        if (sensorDTO != null) {
            return this.service.update(uuid, body.getName(), body.getDescription());
        }

        throw new SensorNotFoundException(uuid);
    }

    @Operation(summary = "Add a new measurementDTO for a sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved measurements")
    }
    )
    @RequestMapping(value = "/sensors/{id}/measurements/", method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    MeasurementDTO addMeasurement(@PathVariable final String id,
                                  @RequestBody MeasurementDTO measurementDTO
    ) {
        return this.service.addMeasurement(id, measurementDTO);
    }

}

package de.freitag.stefan.spring.sht21.server.api;

import de.freitag.stefan.spring.sht21.server.api.model.*;
import de.freitag.stefan.spring.sht21.server.service.SensorService;
import de.freitag.stefan.spring.sht21.server.service.UuidNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(description = "Operations related to sensors and measurements.")
@RestController
public class SensorsApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SensorService service;

    @Autowired
    public SensorsApiController(final SensorService service) {
        this.service = service;
    }

    @ApiOperation(value = "Read the list of sensors", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")
    }
    )
    @RequestMapping(value = "/sensors",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public List<SensorDTO> readSensors() {
        return this.service.readAll();
    }

    @ApiOperation(value = "Read all measurements for a single sensor", response = MeasurementDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved measurements")
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

        SensorDTO sensorDTO = this.service.readByUuid(id);
        if (sensorDTO == null) {
            throw new SensorNotFoundException(id);
        }

        //TODO
        if (from == null || to == null) {
            return this.service.getMeasurements(id);
        } else
            return this.service.getMeasurements(id, from, to);

    }

    @ApiOperation(value = "Create a new sensor.", response = SensorDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sensor was created successfully."),
            @ApiResponse(code = 400, message = "There is a problem with the provided information. E.g. the UUID is null."),
            @ApiResponse(code = 409, message = "There exits already a sensor with the given UUID.")
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

    @ApiOperation(value = "Delete a sensor.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The sensor was deleted successfully."),
            @ApiResponse(code = 404, message = "The sensor could not be found."),
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


    @ApiOperation(value = "Read information for a single sensor", response = SensorDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sensor")
    }
    )
    @RequestMapping(value = "/sensors/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public SensorDTO readSensorByUuid(@PathVariable(name = "id") String uuid) {
        SensorDTO sensorDTO = this.service.readByUuid(uuid);
        if (sensorDTO != null) {
            return sensorDTO;
        } else {
            throw new SensorNotFoundException(uuid);
        }
    }

    @ApiOperation(value = "Update the sensor information", response = SensorDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated sensor")
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

        SensorDTO sensorDTO = this.service.readByUuid(uuid);
        if (sensorDTO != null) {
            return this.service.update(uuid, body.getName(), body.getDescription());
        }

        throw new SensorNotFoundException(uuid);
    }

    @ApiOperation(value = "Add a new measurementDTO for a sensor", response = MeasurementDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved measurements")
    }
    )
    @RequestMapping(value = "/sensors/{id}/measurements/", method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    MeasurementDTO addMeasurement(@PathVariable final String id,
                                  @RequestBody MeasurementDTO measurementDTO
    ) {
        SensorDTO oSensorDTO = this.service.readByUuid(id);
        if (oSensorDTO == null) {
            oSensorDTO = this.service.create(oSensorDTO);
            logger.info("Created new sensor with UUID " + oSensorDTO.getUuid());
        }
        return this.service.addMeasurement(oSensorDTO.getUuid(), measurementDTO);
    }

}

package de.freitag.stefan.spring.sht21.server.api;

import de.freitag.stefan.spring.sht21.server.api.model.*;
import de.freitag.stefan.spring.sht21.server.service.SensorService;
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
import java.math.BigDecimal;
import java.util.List;

@CrossOrigin( origins = "*",  allowedHeaders="*")
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
    public List<Sensor> readSensors() {
        return this.service.readAll();
    }

    @ApiOperation(value = "Read all measurements for a single sensor", response = Measurement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved measurements")
    }
    )
    @RequestMapping(value = "/sensors/{id}/measurements",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public List<Measurement> sensorsIdMeasurementsGet(@PathVariable("id") String id,
                                                      @RequestParam(value = "from", required = false)Long from,
    @RequestParam(value = "to", required = false) Long to) {
        if (!Sensors.isValidUuid(id)){
            throw new InvalidUuidException(id);
        }

        Sensor sensor = this.service.readByUuid(id);
        if (sensor == null) {
            throw new SensorNotFoundException(id);
        }

        //TODO
        if (from==null || to == null) {
            return this.service.getMeasurements(id);
        } else
        return this.service.getMeasurements(id, from, to);

    }

    @ApiOperation(value = "Create a new sensors", response = Sensor.class)
    @RequestMapping(value = "/sensors",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public Sensor createSensor(@Valid @RequestBody Sensor body) {
        if (!Sensors.isValidUuid(body.getUuid())){
            throw new InvalidUuidException(body.getUuid());
        }
        if (this.service.exists(body.getUuid())) {
            throw new SensorUuidAlreadyExistsException(body.getUuid());
        }
        return this.service.create(body.getUuid(), body.getDescription());
    }

    @RequestMapping(value = "/sensors/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> sensorsUuidDelete(BigDecimal uuid) {

        return null;
    }


    @ApiOperation(value = "Read information for a single sensor", response = Sensor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sensor")
    }
    )
    @RequestMapping(value = "/sensors/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public Sensor readSensorByUuid(@PathVariable(name = "id") String uuid) {
        Sensor sensor = this.service.readByUuid(uuid);
        if (sensor != null) {
            return sensor;
        } else {
            throw new SensorNotFoundException(uuid);
        }
    }

    @ApiOperation(value = "Add a new measurement for a sensor", response = Measurement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved measurements")
    }
    )
    @RequestMapping(value = "/sensors/{id}/measurements/", method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    Measurement addMeasurement(@PathVariable final String id,
                                               @RequestBody Measurement measurement
    ) {
        Sensor oSensor = this.service.readByUuid(id);
        if (oSensor==null) {
            oSensor = this.service.create(id, "");
            logger.info("Created new sensor with UUID " + id);
        }
        return this.service.addMeasurement(oSensor.getUuid(), measurement);
    }

}

package de.freitag.stefan.spring.sht21.server.api;

import de.freitag.stefan.spring.sht21.server.api.model.*;
import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
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
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition
@Tag(name = "sensor", description = "The sensor API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Log4j2
public class SensorsApiController {

  private final SensorService service;
  private final ModelMapper modelMapper;

  @Autowired
  public SensorsApiController(@NonNull final SensorService service) {
    this.service = service;
    this.modelMapper = new ModelMapper();
    final MeasurementDTOConverter measurementDTOConverter = new MeasurementDTOConverter();
    this.modelMapper.addConverter(measurementDTOConverter);
    final MeasurementConverter measurementConverter = new MeasurementConverter();
    this.modelMapper.addConverter(measurementConverter);
  }

  @Operation(
      summary = "Read the registered sensors",
      description = "Returns the registered sensors.",
      tags = {"sensor"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful retrieval of all registered sensors.",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = SensorDTO.class))))
      })
  @GetMapping(value = "/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<SensorDTO> readSensors() {
    return this.service.readAll().stream().map(SensorDTO::from).collect(Collectors.toList());
  }

  @Operation(
      summary = "Returns a registered sensor",
      description =
          "Returns a registered sensors or an exception if"
              + "the uuid of the sensor cannot be found..",
      tags = {"sensor"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful retrieval of the sensor.",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = SensorDTO.class)))),
        @ApiResponse(
            responseCode = "404",
            description = "Sensor with given uuid was not found.",
            content = @Content(schema = @Schema(implementation = SensorNotFoundException.class)))
      })
  @GetMapping(value = "/sensors/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
  public SensorDTO readSensor(@NonNull @PathVariable("uuid") String uuid) {
    return this.service
        .readByUuid(uuid)
        .map(SensorDTO::from)
        .orElseThrow(() -> new SensorNotFoundException(uuid));
  }

  @Operation(summary = "Read all measurements for a single sensor.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved measurements")
      })
  @GetMapping(value = "/sensors/{id}/measurements", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<MeasurementDTO> sensorsIdMeasurementsGet(
      @PathVariable("id") String id,
      @RequestParam(value = "from", required = false) Long from,
      @RequestParam(value = "to", required = false) Long to) {
    if (!Sensors.isValidUuid(id)) {
      throw new InvalidUuidException(id);
    }

    // TODO
    if (from == null || to == null) {
      List<Measurement> measurements = this.service.getMeasurements(id);
      return this.convertToDto(measurements);

    } else
      return this.convertToDto(
          this.service.getMeasurements(id, Instant.ofEpochMilli(from), Instant.ofEpochMilli(to)));
  }

  @Operation(summary = "Create a new sensor.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "The sensor was created successfully.",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = SensorDTO.class)))),
        @ApiResponse(
            responseCode = "400",
            description =
                "There is a problem with the provided information. E.g. the UUID is null."),
        @ApiResponse(
            responseCode = "409",
            description = "There exits already a sensor with the given UUID.",
            content =
                @Content(schema = @Schema(implementation = SensorUuidAlreadyExistsException.class)))
      })
  @RequestMapping(
      value = "/sensors",
      produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      method = RequestMethod.POST)
  public SensorDTO createSensor(@Valid @RequestBody SensorDTO body) {
    if (!Sensors.isValidUuid(body.getUuid())) {
      throw new InvalidUuidException(body.getUuid());
    }
    if (this.service.exists(UUID.fromString(body.getUuid()))) {
      throw new SensorUuidAlreadyExistsException(body.getUuid());
    }

    return this.convertToSensorDTO(this.service.create(this.convertToSensor(body)));
  }

  @Operation(summary = "Delete a sensor.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The sensor was deleted successfully."),
        @ApiResponse(responseCode = "404", description = "The sensor could not be found."),
      })
  @RequestMapping(
      value = "/sensors/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE},
      method = RequestMethod.DELETE)
  public ResponseEntity<Void> sensorsUuidDelete(@PathVariable(name = "id") String uuid) {
    try {
      return this.service.delete(uuid);
    } catch (UuidNotFoundException exception) {
      throw new SensorNotFoundException(uuid);
    }
  }

  @Operation(summary = "Update the sensor information")
  @ApiResponses(
      value = {@ApiResponse(responseCode = "200", description = "Successfully updated sensor")})
  @RequestMapping(
      value = "/sensors/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE},
      method = RequestMethod.PUT)
  public SensorDTO updateSensorByUuid(
      @PathVariable(name = "id") final String uuid, @RequestBody SensorDTO body) {

    if (!uuid.equalsIgnoreCase(body.getUuid())) {
      throw new ApiException(
          "Not allowed to update sensorDTO "
              + uuid
              + " with this information. Wrong uuid: "
              + body.getUuid());
    }

    if (body.getName() == null) {
      throw new ApiException("Update sensorDTO with null name is not allowed.");
    }

    Sensor sensorDTO = this.service.readByUuid(uuid).get();
    if (sensorDTO != null) {
      return this.service.update(uuid, body.getName(), body.getDescription());
    }

    throw new SensorNotFoundException(uuid);
  }

  @Operation(summary = "Add a new measurementDTO for a sensor")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved measurements")
      })
  @RequestMapping(
      value = "/sensors/{id}/measurements/",
      method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  MeasurementDTO addMeasurement(
      @PathVariable final String id, @RequestBody MeasurementDTO measurementDTO) {
    Measurement entity = this.convertToEntity(measurementDTO);
    Measurement measurement = this.service.addMeasurement(id, entity);
    return convertToDto(measurement);
  }

  private Sensor convertToSensor(@NonNull final SensorDTO measurement) {
    return modelMapper.map(measurement, Sensor.class);
  }

  private SensorDTO convertToSensorDTO(@NonNull final Sensor measurement) {
    return modelMapper.map(measurement, SensorDTO.class);
  }

  private MeasurementDTO convertToDto(
      @NonNull final de.freitag.stefan.spring.sht21.server.domain.model.Measurement measurement) {
    return modelMapper.map(measurement, MeasurementDTO.class);
  }

  private de.freitag.stefan.spring.sht21.server.domain.model.Measurement convertToEntity(
      @NonNull final MeasurementDTO measurementDTO) {
    return modelMapper.map(
        measurementDTO, de.freitag.stefan.spring.sht21.server.domain.model.Measurement.class);
  }

  private List<MeasurementDTO> convertToDto(
      List<de.freitag.stefan.spring.sht21.server.domain.model.Measurement> post) {
    java.lang.reflect.Type targetListType = new TypeToken<List<MeasurementDTO>>() {}.getType();
    return modelMapper.map(post, targetListType);
  }
}

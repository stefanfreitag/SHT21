package de.freitag.stefan.spring.sht21.server.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
import de.freitag.stefan.spring.sht21.server.service.SensorService;
import de.freitag.stefan.spring.sht21.server.service.UuidNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SensorApiControllerTest {

  public static final String SOME_NONE_EXISTEND_UUID = "someNoneExistendUuid";
  private MockMvc mockMvc;

  @Mock private SensorService sensorService;

  @InjectMocks private SensorsApiController controller;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void fetchAllSensorsReturnsExpectedNumbeAndStatus() throws Exception {
    Sensor sensorDto1 =
        new Sensor(UUID.randomUUID().toString(), "Name of Sensor 1", "Description for Sensor 1.");
    Sensor sensorDto2 =
        new Sensor(UUID.randomUUID().toString(), "Name of Sensor 2", "Description for Sensor 2.");

    List<Sensor> sensors = new ArrayList<>();
    sensors.add(sensorDto1);
    sensors.add(sensorDto2);

    when(sensorService.readAll()).thenReturn(sensors);

    mockMvc
        .perform(get("/sensors/").contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(status().isOk());
  }

  @Test
  public void createSensorWithMissingUuidThrowsBadRequest() throws Exception {
    mockMvc
        .perform(
            post("/sensors/")
                .content(
                    "{ \"description\": \"The description for the sensor\", \"name\": \"The name of the sensor\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createSensorWithAlreadyExistingUuidThrowsConflict() throws Exception {
    when(sensorService.exists(UUID.fromString("e16f9f6c-eb43-4ef7-b7be-48a3653028c9")))
        .thenReturn(true);

    mockMvc
        .perform(
            post("/sensors/")
                .content(
                    "{"
                        + "    \"uuid\": \"e16f9f6c-eb43-4ef7-b7be-48a3653028c9\","
                        + "    \"name\": \"Test\","
                        + "    \"description\": \"Description of the sensor\""
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
  }

  @Test
  public void getNonExistingSensorThrowsSensorNotFoundException() throws Exception {
    when(sensorService.readByUuid(SOME_NONE_EXISTEND_UUID)).thenReturn(Optional.empty());

    mockMvc
        .perform(get("/sensors/" + SOME_NONE_EXISTEND_UUID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteExistingSensorReturnsExpectedStatus() throws Exception {
    String uuid1 = UUID.randomUUID().toString();

    when(sensorService.delete(uuid1)).thenReturn(null);

    mockMvc
        .perform(delete("/sensors/" + uuid1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteNonExistingSensorReturnsExpectedStatus() throws Exception {
    String uuid1 = UUID.randomUUID().toString();

    when(sensorService.delete(uuid1)).thenThrow(new UuidNotFoundException("Sensor UUID"));

    mockMvc
        .perform(delete("/sensors/" + uuid1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}

package de.freitag.stefan.spring.sht21.server.api;

import de.freitag.stefan.spring.sht21.server.api.model.SensorDTO;
import de.freitag.stefan.spring.sht21.server.service.SensorService;
import de.freitag.stefan.spring.sht21.server.service.UuidNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SensorApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private SensorsApiController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void fetchAllSensorsReturnsExpectedNumbeAndStatus() throws Exception {
        SensorDTO sensorDto1 = new SensorDTO(UUID.randomUUID().toString(), "Name of SensorDTO 1", "Description for SensorDTO 1.");
        SensorDTO sensorDto2 = new SensorDTO(UUID.randomUUID().toString(), "Name of SensorDTO 2", "Description for SensorDTO 2.");

        List<SensorDTO> sensorDTOS = new ArrayList<>();
        sensorDTOS.add(sensorDto1);
        sensorDTOS.add(sensorDto2);

        when(sensorService.readAll()).thenReturn(sensorDTOS);

        mockMvc.perform(get("/sensors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }


    @Test
    public void createSensorWithMissingUuidThrowsBadRequest() throws Exception {
        mockMvc.perform(post("/sensors/")
                .content(
                                "{ \"description\": \"The description for the sensor\", \"name\": \"The name of the sensor\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void createSensorWithAlreadyExistingUuidThrowsConflict() throws Exception {
        when(sensorService.exists("e16f9f6c-eb43-4ef7-b7be-48a3653028c9")).thenReturn(true);

        mockMvc.perform(post("/sensors/")
                .content("{" +
                        "    \"uuid\": \"e16f9f6c-eb43-4ef7-b7be-48a3653028c9\"," +
                        "    \"name\": \"Test\"," +
                        "    \"description\": \"Description of the sensor\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteExistingSensorReturnsExpectedStatus() throws Exception {
        String uuid1 = UUID.randomUUID().toString();

        when(sensorService.delete(uuid1)).thenReturn(null);

        mockMvc.perform(delete("/sensors/" + uuid1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteNonExistingSensorReturnsExpectedStatus() throws Exception {
        String uuid1 = UUID.randomUUID().toString();

        when(sensorService.delete(uuid1)).thenThrow(new UuidNotFoundException("Sensor UUID"));

        mockMvc.perform(delete("/sensors/" + uuid1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
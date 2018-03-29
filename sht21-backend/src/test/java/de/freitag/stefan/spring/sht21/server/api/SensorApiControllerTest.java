package de.freitag.stefan.spring.sht21.server.api;

import de.freitag.stefan.spring.sht21.server.api.model.Sensor;
import de.freitag.stefan.spring.sht21.server.service.SensorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        Sensor sensor_1 = new Sensor(UUID.randomUUID().toString(), "Name of Sensor 1", "Description for Sensor 1.");
        Sensor sensor_2 = new Sensor(UUID.randomUUID().toString(), "Name of Sensor 2", "Description for Sensor 2.");

        List<Sensor> sensors = new ArrayList<>();
        sensors.add(sensor_1);
        sensors.add(sensor_2);

        when(sensorService.readAll()).thenReturn(sensors);

        mockMvc.perform(get("/sensors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

}
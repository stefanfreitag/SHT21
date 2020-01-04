package de.freitag.stefan.spring.sht21.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
import de.freitag.stefan.spring.sht21.server.domain.repositories.SensorRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SensorServiceTest {

  @Mock private SensorRepository repository;

  @Mock private ModelMapper mapper;
  @Mock private InfluxService influxService;
  @InjectMocks private SensorService service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @DisplayName("Positive check for sensor existence")
  @Test
  void existsForExistingUuid() {
    UUID uuid = UUID.randomUUID();
    when(repository.findByUuid(uuid.toString()))
        .thenReturn(
            Optional.of(
                Sensor.builder()
                    .uuid(uuid.toString())
                    .name("Sensor name")
                    .description("Sensor description")
                    .build()));
    assertTrue(service.exists(uuid));
  }

  @DisplayName("Negative check for sensor existence")
  @Test
  void existsForNonExistingUuid() {
    UUID uuid = UUID.randomUUID();
    when(repository.findByUuid(uuid.toString())).thenReturn(Optional.empty());
    assertFalse(service.exists(uuid));
  }

  @DisplayName("Reading all sensors.")
  @Test
  void readAllSensors() {
    verifyNoInteractions(repository);
    service.readAll();
    verify(repository, times(0)).findAll();
    verifyNoMoreInteractions();
  }

  @DisplayName("Create call for an existing sensor.")
  @Test
  void createForExistingSensor() {
    final UUID uuid = UUID.randomUUID();
    final Sensor sensor =
        Sensor.builder().uuid(uuid.toString()).name("Sensor 1").description("Description").build();
    when(this.repository.findByUuid(uuid.toString())).thenReturn(Optional.of(sensor));

    Sensor result = service.create(sensor);
    assertEquals(sensor, result);
  }

  @DisplayName("Create call for a non-existing sensor.")
  @Test
  void createForNonExistingSensor() {
    final UUID uuid = UUID.randomUUID();
    final Sensor sensor =
        Sensor.builder().uuid(uuid.toString()).name("Sensor 1").description("Description").build();
    when(this.repository.findByUuid(uuid.toString())).thenReturn(Optional.empty());
    Sensor result = service.create(sensor);

    verify(repository, times(1)).save(sensor);
    assertEquals(sensor, result);
  }
}

package de.freitag.stefan.spring.sht21.server.service;

import de.freitag.stefan.spring.sht21.server.api.model.MeasurementDTO;
import de.freitag.stefan.spring.sht21.server.api.model.SensorDTO;
import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
import de.freitag.stefan.spring.sht21.server.domain.repositories.MeasurementRepository;
import de.freitag.stefan.spring.sht21.server.domain.repositories.SensorRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Log4j2
@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository repository;

    private final MeasurementRepository measurementRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorServiceImpl(final @NonNull SensorRepository repository, ModelMapper modelMapper, MeasurementRepository measurementRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.measurementRepository = measurementRepository;
    }

    @Override
    public List<Sensor> readAll() {
        return
                StreamSupport.stream(this.repository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
    }

    @Override
    public SensorDTO create(@NonNull  final SensorDTO sensorDTO) {
        Optional<Sensor> sensor = this.repository.findByUuid(sensorDTO.getUuid());
        if (!sensor.isPresent()){
            Stream.of(sensorDTO).map(this::convertToEntity).forEach(repository::save);
        return sensorDTO;
        }
        return null;
    }

    @Override
    public SensorDTO update(final String uuid, final String name, final String description) {
        Optional<Sensor> sensor = this.repository.findByUuid(uuid);
        sensor.get().setName(name);
        sensor.get().setDescription(description);
        //TODO
        //Sensor save = this.repository.save(sensor);
//        return this.convertToDto(sensor.get());
        return null;
    }

    @Override
    public Optional<Sensor> readByUuid(@NonNull final String uuid) {
        log.info("Finding sensor with uuid " + uuid);
        return this.repository.findByUuid(uuid);

    }

    @Override
    public boolean exists(@NonNull final String uuid) {
        System.out.println(uuid);
        System.out.println(this.repository.findByUuid(uuid).isPresent());
        System.out.println(this.repository.findAll());
        return this.repository.findByUuid(uuid).isPresent();
    }

    @Override
    public List<MeasurementDTO> getMeasurements(final String uuid) {
        //Sensor sensor = this.repository.findByUuid(uuid);
        return Collections.emptyList();//TODO: this.convertToDto(sensor.getMeasurements());
    }

    @Override
    public List<MeasurementDTO> getMeasurements(final String uuid, final Long from, final Long to) {
        List<Measurement> m = this.measurementRepository.findByMeasuredAtBetweenAndSensor_Id(from, to, uuid);
        log.info("Getting measurements for sensor with uuid " + uuid);
        return this.convertToDto(m);
    }

    @Override
    public MeasurementDTO addMeasurement(@NonNull final String uuid, @NonNull final MeasurementDTO measurementDTO) {
        Measurement entity = this.convertToEntity(measurementDTO);
        //Optional<Sensor> byUuid = this.repository.findByUuid(uuid);
        //entity.setSensor(byUuid.get());
        //TODO
        //this.measurementRepository.save(entity);
        //byUuid.add(entity);
        //this.repository.save(byUuid.get());
        measurementRepository.save(entity);
        log.info("Added new measurement for sensor " + uuid + ": " + entity);
        return this.convertToDto(entity);
    }

    @Override
    public ResponseEntity<Void> delete(final String uuid) throws UuidNotFoundException {
        Optional<Sensor> sensor = this.repository.findByUuid(uuid);
        if (sensor == null) {
            throw new UuidNotFoundException("Could not find sensor with uuid " + uuid);
        }

        this.repository.delete(sensor.get());
        log.info("Deleted sensor with uuid " + uuid);
        return null;
    }

    private de.freitag.stefan.spring.sht21.server.domain.model.Sensor convertToEntity(SensorDTO postDto) {
        de.freitag.stefan.spring.sht21.server.domain.model.Sensor post = modelMapper.map(postDto, de.freitag.stefan.spring.sht21.server.domain.model.Sensor.class);
        return post;
    }


    private MeasurementDTO convertToDto(de.freitag.stefan.spring.sht21.server.domain.model.Measurement post) {
        return modelMapper.map(post, MeasurementDTO.class);
    }


    private List<MeasurementDTO> convertToDto(List<de.freitag.stefan.spring.sht21.server.domain.model.Measurement> post) {
        java.lang.reflect.Type targetListType = new TypeToken<List<MeasurementDTO>>() {
        }.getType();
        return modelMapper.map(post, targetListType);
    }

    private de.freitag.stefan.spring.sht21.server.domain.model.Measurement convertToEntity(MeasurementDTO postDto) {
        de.freitag.stefan.spring.sht21.server.domain.model.Measurement post = modelMapper.map(postDto, de.freitag.stefan.spring.sht21.server.domain.model.Measurement.class);
        return post;
    }
}

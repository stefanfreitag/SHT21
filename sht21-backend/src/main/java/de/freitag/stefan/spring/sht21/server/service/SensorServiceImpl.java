package de.freitag.stefan.spring.sht21.server.service;

import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import de.freitag.stefan.spring.sht21.server.domain.model.Sensor;
import de.freitag.stefan.spring.sht21.server.domain.repositories.MeasurementRepository;
import de.freitag.stefan.spring.sht21.server.domain.repositories.SensorRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository repository;

    private final MeasurementRepository measurementRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorServiceImpl(SensorRepository repository, ModelMapper modelMapper, MeasurementRepository measurementRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.measurementRepository = measurementRepository;
    }

    @Override
    public List<de.freitag.stefan.spring.sht21.server.api.model.Sensor> readAll() {
        return
                StreamSupport.stream(this.repository.findAll().spliterator(), false)
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    @Override
    public de.freitag.stefan.spring.sht21.server.api.model.Sensor create(final String uuid, final String description) {
        Sensor sensor = new Sensor();
        sensor.setUuid(uuid);
        sensor.setDescription(description);
        Sensor save = this.repository.save(sensor);

        return this.convertToDto(save);
    }

    @Override
    public de.freitag.stefan.spring.sht21.server.api.model.Sensor update(final String uuid, final String name, final String description) {
        Sensor sensor = this.repository.findByUuid(uuid);
        sensor.setName(name);
        sensor.setDescription(description);
        Sensor save = this.repository.save(sensor);
        return this.convertToDto(save);
    }

    @Override
    public de.freitag.stefan.spring.sht21.server.api.model.Sensor readByUuid(final String uuid) {
        log.info("Finding sensor with uuid " + uuid);
        Sensor sensor = this.repository.findByUuid(uuid);
        if (sensor!=null) {
            return this.convertToDto(sensor);
        }
        return null;
    }

    @Override
    public boolean exists(final String uuid) {
        return this.repository.findByUuid(uuid)!=null;
    }

    @Override
    public List<de.freitag.stefan.spring.sht21.server.api.model.Measurement> getMeasurements(final String uuid) {
        Sensor sensor = this.repository.findByUuid(uuid);
        return this.convertToDto(sensor.getMeasurements());
    }

    @Override
    public List<de.freitag.stefan.spring.sht21.server.api.model.Measurement> getMeasurements(final String uuid, final Long from, final Long to) {
        List<Measurement> m = this.measurementRepository.findByMeasuredAtBetweenAndSensor_Id(from, to,uuid);
        log.info("Getting measurements for sensor with uuid " + uuid);
        return this.convertToDto(m);
    }

    @Override
    public de.freitag.stefan.spring.sht21.server.api.model.Measurement addMeasurement(final String uuid, final de.freitag.stefan.spring.sht21.server.api.model.Measurement measurement) {
        Measurement entity = this.convertToEntity(measurement);
        Sensor byUuid = this.repository.findByUuid(uuid);
        entity.setSensor(byUuid);
        this.measurementRepository.save(entity);
        byUuid.add(entity);
        this.repository.save(byUuid);
        log.info("Added new measurement for sensor " + byUuid.getUuid());
        return this.convertToDto(entity);
    }

    private de.freitag.stefan.spring.sht21.server.api.model.Sensor convertToDto(@NonNull de.freitag.stefan.spring.sht21.server.domain.model.Sensor post) {
        log.info("Converting to Dto" + post);
        return modelMapper.map(post, de.freitag.stefan.spring.sht21.server.api.model.Sensor.class);
    }

    private de.freitag.stefan.spring.sht21.server.domain.model.Sensor convertToEntity(de.freitag.stefan.spring.sht21.server.api.model.Sensor postDto) {
        de.freitag.stefan.spring.sht21.server.domain.model.Sensor post = modelMapper.map(postDto, de.freitag.stefan.spring.sht21.server.domain.model.Sensor.class);
        return post;
    }


    private de.freitag.stefan.spring.sht21.server.api.model.Measurement convertToDto(de.freitag.stefan.spring.sht21.server.domain.model.Measurement post) {
        return modelMapper.map(post,de.freitag.stefan.spring.sht21.server.api.model.Measurement.class) ;
    }


    private List<de.freitag.stefan.spring.sht21.server.api.model.Measurement> convertToDto(List<de.freitag.stefan.spring.sht21.server.domain.model.Measurement> post) {
        java.lang.reflect.Type targetListType = new TypeToken<List<de.freitag.stefan.spring.sht21.server.api.model.Measurement>>() {}.getType();
        return modelMapper.map(post, targetListType);
    }

    private de.freitag.stefan.spring.sht21.server.domain.model.Measurement convertToEntity(de.freitag.stefan.spring.sht21.server.api.model.Measurement postDto) {
        de.freitag.stefan.spring.sht21.server.domain.model.Measurement post = modelMapper.map(postDto, de.freitag.stefan.spring.sht21.server.domain.model.Measurement.class);
        return post;
    }
}

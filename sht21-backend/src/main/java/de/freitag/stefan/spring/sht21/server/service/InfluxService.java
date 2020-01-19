package de.freitag.stefan.spring.sht21.server.service;

import de.freitag.stefan.spring.sht21.server.domain.model.Measurement;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.stereotype.Service;

@Service
public class InfluxService {

  private InfluxDB influxDB;
  private InfluxDBResultMapper resultMapper;
  private String dbName = "sht_21";
  private String measurementName = dbName + "_measurements";

  InfluxService() {
    this.influxDB = InfluxDBFactory.connect("http://192.168.178.20:8086", "root", "root");
    this.resultMapper = new InfluxDBResultMapper();
    influxDB.setLogLevel(InfluxDB.LogLevel.FULL);
    this.createDataBase();
  }

  private void createDataBase() {
    influxDB.query(new Query("CREATE DATABASE " + dbName));

    influxDB.setDatabase(dbName);
    String rpName = "sht_21_retentionPolicy";
    influxDB.query(
        new Query(
            "CREATE RETENTION POLICY "
                + rpName
                + " ON "
                + dbName
                + " DURATION 365d REPLICATION 1 SHARD DURATION 30m DEFAULT"));
    influxDB.setRetentionPolicy(rpName);
  }

  public void writeMeasurement(@NonNull final String uuid, @NonNull final Measurement measurement) {

    influxDB.write(
        Point.measurement(measurementName)
            .time(measurement.getMeasuredAt().toEpochMilli(), TimeUnit.MILLISECONDS)
            .tag("uuid", uuid)
            .tag("type", "temperature")
            .addField("value", measurement.getValue())
            .addField("unit", measurement.getUnit())
            .build());
  }

  public List<Measurement> getMeasurements(@NonNull final String uuid) {
    final QueryResult queryResult =
        influxDB.query(
            new Query("Select * from " + measurementName + " where uuid='" + uuid + "';", dbName));
    return resultMapper.toPOJO(queryResult, Measurement.class);
  }

  public List<Measurement> getMeasurements(
      @NonNull final String uuid, final Instant from, final Instant to) {
    Query query =
        new Query(
            "Select * from "
                + measurementName
                + " where time >="
                + from.toEpochMilli()
                + "ms AND time <="
                + to.toEpochMilli()
                + "ms",
            dbName);
    // TODO: Add uuid to query
    QueryResult queryResult = influxDB.query(query);
    return resultMapper.toPOJO(queryResult, Measurement.class);
  }
}

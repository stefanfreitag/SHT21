package de.freitag.stefan.sht21.rest;

import de.freitag.stefan.sht21.SHT21DummyImpl;
import de.freitag.stefan.sht21.model.EndOfBatteryAlert;
import de.freitag.stefan.sht21.model.HeaterStatus;
import de.freitag.stefan.sht21.model.Measurement;
import de.freitag.stefan.sht21.model.Resolution;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Controller.
 *
 * @author Stefan Freitag
 */
@Path("/sht21")
@Produces(MediaType.APPLICATION_JSON)
public final class SHT21Controller {

    /**
     * The internal task executing the measurements.
     */
    private final MeasurementTask task;


    /**
     * Create a new {@link SHT21Controller}.
     *
     * @param configuration A {@link SHT21RestConfiguration}.
     */
    private SHT21Controller(final SHT21RestConfiguration configuration) {
        //this.task = MeasurementTask.create(SHT21Impl.create(1, 0x40), configuration.getStartDelay(), configuration.getInterval());
        this.task = MeasurementTask.create(new SHT21DummyImpl(), configuration.getStartDelay(), configuration.getInterval());
    }

    /**
     * Create a new {@link SHT21Controller}.
     *
     * @param configuration A {@link SHT21RestConfiguration}.
     * @return A new {@link SHT21Controller}.
     * @throws IllegalArgumentException if {@code configuration} is {@code null}.
     */
    public static SHT21Controller create(final SHT21RestConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("SHT21RestConfiguration is null");
        }
        return new SHT21Controller(configuration);
    }


    @GET
    @Path("/getTemperature")
    public Measurement getTemperature() {
        return this.task.getTemperature();
    }

    @GET
    @Path("/getHumidity")
    public Measurement getHumidity() {
        return this.task.getHumidity();
    }

    @GET
    @Path("/getResolution")
    public Resolution getResolution() {
        return this.task.getResolution();
    }

    @GET
    @Path("/getHeaterStatus")
    public HeaterStatus getHeaterStatus() {
        return this.task.getHeaterStatus();
    }

    @GET
    @Path("/getEoBAlert")
    public EndOfBatteryAlert getEobStatus() {
        return this.task.getEobStatus();
    }
}

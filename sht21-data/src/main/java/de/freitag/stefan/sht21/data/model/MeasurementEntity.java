package de.freitag.stefan.sht21.data.model;

import de.freitag.stefan.sht21.model.MeasureType;
import de.freitag.stefan.sht21.model.Measurement;

import java.util.Date;
import java.util.Objects;

/**
 * Created by stefan on 12.07.16.
 */
public class MeasurementEntity {
    private Long id;
    private Date createdAt;
    private float value;
    private MeasureType type;


    /**
     * Return the measured value.
     *
     * @return measured value
     */
    public float getValue() {
        return this.value;
    }

    public void setValue(final float value) {
        this.value = value;
    }

    /**
     * Return the {@link MeasureType}.
     *
     * @return The {@link MeasureType}.
     */
    public MeasureType getType() {
        return this.type;
    }

    public void setType(final MeasureType type) {
        this.type = type;
    }

    /**
     * Return the creation {@link Date}.
     *
     * @return The creation {@link Date}.
     */
    public Date getCreatedAt() {
        return new Date(this.createdAt.getTime());
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementEntity that = (MeasurementEntity) o;
        return Float.compare(that.getValue(), getValue()) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt()) &&
                getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getCreatedAt(), getValue(), getType());
    }

    @Override
    public String toString() {
        return "MeasurementEntity{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", type=" + type +
                ", value=" + value +
                '}';
    }

    Measurement toMeasurement() {
        return Measurement.create(this.value, this.type, this.createdAt);

    }
}

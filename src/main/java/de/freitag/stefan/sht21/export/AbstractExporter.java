package de.freitag.stefan.sht21.export;

import java.util.Objects;

/**
 * Base class for all exporters.
 */
public abstract class AbstractExporter implements Exporter {
    /**
     * The name of this exporter.
     */
    private String name;

    /**
     * A brief description about the purpose of this exporter.
     */
    private String description;

    /**
     * Create a new {@link AbstractExporter}
     *
     * @param name        The non-null and non-empty name of the exporter.
     * @param description The non-null description of the exporter.
     */
    protected AbstractExporter(final String name, final String description) {
        Objects.requireNonNull(name, "Name of exporter has to be non-null.");
        Objects.requireNonNull(name, "Description of exporter has to be non-null.");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Empty exporter name is not allowed.");
        }
        this.name = name;
        this.description = description;
    }

    /**
     * Return the exporter name.
     *
     * @return Name of the exporter.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Return the description for the exporter.
     *
     * @return Description for the exporter.
     */
    public final String getDescription() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractExporter that = (AbstractExporter) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription());
    }

    @Override
    public String toString() {
        return "AbstractExporter{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

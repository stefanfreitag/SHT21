package de.freitag.stefan.sht21.task;

import java.util.UUID;

/**
 * A piece of work that can be started and also cancelled.
 * It has to provide a unique identifier.
 */
public interface Task {

    /**
     * Return the unique identifier for the task.
     *
     * @return A unique identifier for the task.
     */
    UUID getIdentifier();

    /**
     * Start the task.
     */
    void start();

    /**
     * Check if the task is started.
     *
     * @return {@code true} if the task is started, otherwise
     * {@code false} is returned.
     */
    boolean isStarted();

    /**
     * Cancel the task.
     */
    void cancel();

    /**
     * Check if the task is canceled.
     *
     * @return {@code true} if the task is canceled, otherwise
     * {@code false} is returned.
     */
    boolean isCanceled();
}

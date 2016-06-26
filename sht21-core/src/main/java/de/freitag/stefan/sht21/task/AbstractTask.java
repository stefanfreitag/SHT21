package de.freitag.stefan.sht21.task;

import java.util.UUID;

abstract class AbstractTask implements Task {

    /**
     * The unique identifier of this task.
     */
    private final UUID uuid;

    private boolean started;

    private boolean canceled;

    /**
     * Create a new {@link AbstractTask},
     */
    AbstractTask() {
        super();
        this.uuid = UUID.randomUUID();
    }

    /**
     * Return the unique id of this task.
     *
     * @return {@link UUID} of this task.
     */
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public void start() {
        this.started = true;

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }


}

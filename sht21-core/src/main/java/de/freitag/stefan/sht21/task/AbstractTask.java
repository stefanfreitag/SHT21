package de.freitag.stefan.sht21.task;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import lombok.Getter;

@Getter
abstract class AbstractTask implements Task {

  /** The unique identifier of this task. */
  private final UUID identifier;

  private boolean started;

  private boolean canceled;

  private ScheduledExecutorService service;

  /** Create a new {@link AbstractTask}, */
  AbstractTask() {
    super();
    this.identifier = UUID.randomUUID();
    this.service = Executors.newScheduledThreadPool(1);
  }

  @Override
  public void start() {
    this.started = true;
  }

  @Override
  public void cancel() {
    this.canceled = true;
  }

  public boolean isCanceled() {
    return this.canceled;
  }

  public boolean isStarted() {
    return this.started;
  }
}

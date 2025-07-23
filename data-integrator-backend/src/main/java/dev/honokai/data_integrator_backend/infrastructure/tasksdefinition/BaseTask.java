package dev.honokai.data_integrator_backend.infrastructure.tasksdefinition;

import dev.honokai.data_integrator_backend.domain.entities.Task;

import java.time.Duration;

public class BaseTask implements Runnable {
    private Task task;

    public BaseTask() {
        // TODO Auto-generated constructor stub
    }

    public int getScanIntervalInSeconds() {
        return this.task.getScanInterval();
    }

    public Duration getScanIntervalDurationInSeconds() {
        return Duration.ofSeconds(this.task.getScanInterval());
    }

    public void setScanIntervalInSeconds(int scanIntervalInSeconds) {
        this.task.setScanInterval(scanIntervalInSeconds);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Extending class must implement the method itself");
    }
}

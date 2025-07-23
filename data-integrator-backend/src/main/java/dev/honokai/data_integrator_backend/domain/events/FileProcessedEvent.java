package dev.honokai.data_integrator_backend.domain.events;

import dev.honokai.data_integrator_backend.domain.entities.Job;
import org.springframework.context.ApplicationEvent;

public class FileProcessedEvent extends ApplicationEvent {
    public FileProcessedEvent(Job job) {
        super(job);
    }
}

package dev.honokai.data_integrator_backend.domain.events;

import dev.honokai.data_integrator_backend.application.dtos.JobForQueue;
import dev.honokai.data_integrator_backend.domain.entities.Job;
import org.springframework.context.ApplicationEvent;

public class FileFoundForProcessingEvent extends ApplicationEvent {
    private final Job job;

    public FileFoundForProcessingEvent(Object source, Job job) {
        super(source);
        this.job = job;
    }

    public JobForQueue getJob() {
        System.out.printf("%s disparado", this.getClass().getSimpleName());
        
        return new JobForQueue(this.job.getId(), this.job.getTask().getId(), this.job.getPath());
    }
}

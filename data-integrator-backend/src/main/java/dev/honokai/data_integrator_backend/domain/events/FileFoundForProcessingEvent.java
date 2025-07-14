package dev.honokai.data_integrator_backend.domain.events;

import dev.monitor.tasks.application.dtos.JobForQueue;
import dev.monitor.tasks.domain.entities.Job;
import org.springframework.context.ApplicationEvent;

public class FileFoundForProcessingEvent extends ApplicationEvent {
	private final Job job;

	public FileFoundForProcessingEvent(Object source, Job job) {
		super(source);
		this.job = job;
	}

	public JobForQueue getJob() {
		System.out.println("evento disparado");
		JobForQueue queueJob = new JobForQueue(this.job.getId(), this.job.getTask().getId(), this.job.getPath());

		return queueJob;
	}
}

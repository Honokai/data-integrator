package dev.monitor.tasks.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.monitor.tasks.domain.entities.Job;
import dev.monitor.tasks.domain.enums.JobStatus;
import dev.monitor.tasks.infrastructure.repositories.JobRepository;

@Service
public class JobService {
	@Autowired
	private JobRepository jobRepository;

	public Job create(Job jobToCreate) {
		return jobRepository.save(jobToCreate);
	}

	public Optional<Job> getJobByTaskIdAndPath(String taskId, String networkPath) {
		return jobRepository.findByTaskIdAndPath(taskId, networkPath);
	}

	public Optional<Job> findByTaskIdAndPathAndStatus(String taskId, String networkPath, JobStatus status) {
		return jobRepository.findByTaskIdAndPathAndStatus(taskId, networkPath, status);
	}

	public Job updateJob(Job job) {
		return jobRepository.save(job);
	}
}

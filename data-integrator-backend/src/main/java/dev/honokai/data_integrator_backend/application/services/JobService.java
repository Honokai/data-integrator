package dev.honokai.data_integrator_backend.application.services;

import dev.honokai.data_integrator_backend.domain.entities.Job;
import dev.honokai.data_integrator_backend.domain.enums.JobStatus;
import dev.honokai.data_integrator_backend.infrastructure.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

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

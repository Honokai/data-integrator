package dev.honokai.data_integrator_backend.infrastructure.repositories;

import dev.honokai.data_integrator_backend.domain.entities.Job;
import dev.honokai.data_integrator_backend.domain.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, String> {
	public Optional<Job> findByTaskIdAndPath(String taskId, String path);

	public Optional<Job> findByTaskIdAndPathAndStatus(String taskId, String path, JobStatus status);
}

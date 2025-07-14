package dev.honokai.data_integrator_backend.infrastructure.repositories;

import dev.honokai.data_integrator_backend.domain.entities.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScriptRepository extends JpaRepository<Script, String> {
	public Optional<Script> findByTaskIdAndActiveTrue(String taskId);
}

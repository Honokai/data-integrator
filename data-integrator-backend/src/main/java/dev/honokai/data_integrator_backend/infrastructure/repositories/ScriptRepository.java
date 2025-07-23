package dev.honokai.data_integrator_backend.infrastructure.repositories;

import dev.honokai.data_integrator_backend.domain.entities.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScriptRepository extends JpaRepository<Script, String> {
    Optional<Script> findByTaskIdAndActiveTrue(String taskId);

    List<Script> findByTaskId(String taskId);
}

package dev.honokai.data_integrator_backend.infrastructure.repositories;

import dev.honokai.data_integrator_backend.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    @Query("select t from Task t where t.active = true")
    public List<Task> findAllActive();

    public List<Task> findByMachineId(String machineId);

    @Query("select t from Task t join Script s on s.task.id = t.id where t.active = true and s.active = true")
    public List<Task> findByTaskActiveTrueAndScriptActiveTrue();
}

package dev.honokai.data_integrator_backend.infrastructure.repositories;

import dev.honokai.data_integrator_backend.domain.entities.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, String> {

}

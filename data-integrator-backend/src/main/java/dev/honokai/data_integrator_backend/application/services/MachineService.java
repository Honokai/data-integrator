package dev.honokai.data_integrator_backend.application.services;

import dev.honokai.data_integrator_backend.domain.entities.Machine;
import dev.honokai.data_integrator_backend.infrastructure.repositories.MachineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public List<Machine> listAll() {
        return machineRepository.findAll();
    }

    public Machine listOne(String machineId) {
        return machineRepository.findById(machineId).orElseThrow(EntityNotFoundException::new);
    }

    public Machine create(Machine machine) {
        return machineRepository.save(machine);
    }
}

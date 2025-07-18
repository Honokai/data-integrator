package dev.honokai.data_integrator_backend.application.services;

import dev.honokai.data_integrator_backend.domain.entities.Machine;
import dev.honokai.data_integrator_backend.infrastructure.repositories.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public List<Machine> listAll() {
        return machineRepository.findAll();
    }

    public Optional<Machine> listOne(String machineId) {
        return machineRepository.findById(machineId);
    }

    public Machine create(Machine machine) {
        return machineRepository.save(machine);
    }
}

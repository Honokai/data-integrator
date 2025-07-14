package dev.honokai.data_integrator_backend.application.services;

import dev.honokai.data_integrator_backend.domain.entities.Machine;
import dev.honokai.data_integrator_backend.infrastructure.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {
    @Autowired
    private MachineRepository machineRepository;

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

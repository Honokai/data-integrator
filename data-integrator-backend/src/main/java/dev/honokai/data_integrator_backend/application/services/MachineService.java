package dev.monitor.tasks.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.monitor.tasks.domain.entities.Machine;
import dev.monitor.tasks.infrastructure.repositories.MachineRepository;

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

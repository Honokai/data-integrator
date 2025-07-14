package dev.monitor.tasks.application.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.monitor.tasks.application.converter.GenericConverter;
import dev.monitor.tasks.application.dtos.MachineIndexDto;
import dev.monitor.tasks.application.dtos.MachinePostDto;
import dev.monitor.tasks.application.dtos.TaskResponseDto;
import dev.monitor.tasks.application.services.MachineService;
import dev.monitor.tasks.application.services.TaskService;
import dev.monitor.tasks.domain.entities.Machine;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/machines")
public class MachineController {
	@Autowired
	private MachineService machineService;

	@Autowired
	private TaskService taskService;

	@GetMapping
	public ResponseEntity<List<MachineIndexDto>> index() {
		return ResponseEntity.ok(machineService.listAll().stream().map(machine -> new MachineIndexDto(machine))
				.collect(Collectors.toList()));
	}

	@GetMapping("/{machineId}")
	public ResponseEntity<MachineIndexDto> edit(@PathVariable(name = "machineId") String machineId) {
		Optional<Machine> machine = machineService.listOne(machineId);

		System.out.println(machine.get());

		return machine.map(m -> ResponseEntity.ok(new MachineIndexDto(m))).orElse(ResponseEntity.notFound().build());
//		return ResponseEntity.ok(machineService.listOne(machineId));
	}

	@PostMapping
	public ResponseEntity<Machine> create(@Valid @RequestBody MachinePostDto machineDto) {
		return ResponseEntity.ok(machineService.create(GenericConverter.mapTo(machineDto, Machine.class)));
	}

	@GetMapping("/{machineId}/tasks")
	public ResponseEntity<List<TaskResponseDto>> getTasksRelatedToMachine(@PathVariable String machineId) {

		return ResponseEntity.ok(taskService.findTasksRelatedToMachine(machineId).stream()
				.map(t -> new TaskResponseDto(t)).collect(Collectors.toList()));
	}
}

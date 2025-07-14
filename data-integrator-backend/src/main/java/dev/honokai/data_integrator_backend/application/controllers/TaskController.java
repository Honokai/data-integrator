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

import dev.monitor.tasks.application.dtos.TaskCreateDto;
import dev.monitor.tasks.application.dtos.TaskResponseDto;
import dev.monitor.tasks.application.services.MachineService;
import dev.monitor.tasks.application.services.TaskService;
import dev.monitor.tasks.domain.entities.Machine;
import dev.monitor.tasks.domain.entities.Task;
import dev.monitor.tasks.infrastructure.services.SchedulerService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;

	@Autowired
	private MachineService machineService;

	@Autowired
	private SchedulerService schedulerService;

	@GetMapping
	public ResponseEntity<List<TaskCreateDto>> index() {
		return ResponseEntity.ok(taskService.listAll().stream().map(t -> new TaskCreateDto(t.getMachine().getName(),
				t.getNetworkPath(), t.getScanInterval(), t.isSingleFile(), t.isActive())).collect(Collectors.toList()));
	}

	@PostMapping
	public ResponseEntity<Task> create(@RequestBody TaskCreateDto taskToCreate) {
		Optional<Machine> machine = machineService.listOne(taskToCreate.getMachine());

		Task taskCreated = taskService.create(new Task(machine.get(), taskToCreate.getNetworkPath(),
				taskToCreate.getScanInterval(), taskToCreate.isSingleFile(), taskToCreate.isActive()));

		return ResponseEntity.ok(taskCreated);
	}

	@PostMapping("/{taskIdentifier}/stop")
	public ResponseEntity<String> stop(@PathVariable String taskIdentifier) {
		boolean stoped = schedulerService.stopScheduledTask(taskIdentifier);

		if (!stoped) {
			return ResponseEntity.ok("Verificar tarefa");
		}

		return ResponseEntity.ok("Tarefa agendada cancelada");
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<TaskResponseDto> edit(@PathVariable String taskId) {
		return taskService.findById(taskId).map(task -> ResponseEntity.ok(new TaskResponseDto(task)))
				.orElse(ResponseEntity.notFound().build());
	}
}

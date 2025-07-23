package dev.honokai.data_integrator_backend.application.controllers;

import dev.honokai.data_integrator_backend.application.dtos.script.ScriptResponseDto;
import dev.honokai.data_integrator_backend.application.dtos.task.TaskCreateDto;
import dev.honokai.data_integrator_backend.application.dtos.task.TaskResponseDto;
import dev.honokai.data_integrator_backend.application.dtos.task.TaskUpdateDto;
import dev.honokai.data_integrator_backend.application.services.MachineService;
import dev.honokai.data_integrator_backend.application.services.TaskService;
import dev.honokai.data_integrator_backend.domain.entities.Machine;
import dev.honokai.data_integrator_backend.domain.entities.Task;
import dev.honokai.data_integrator_backend.infrastructure.services.SchedulerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final MachineService machineService;
    private final SchedulerService schedulerService;

    public TaskController(TaskService taskService, MachineService machineService, SchedulerService schedulerService) {
        this.taskService = taskService;
        this.machineService = machineService;
        this.schedulerService = schedulerService;
    }

    @GetMapping
    public ResponseEntity<List<TaskCreateDto>> index() {
        return ResponseEntity.ok(taskService.listAll().stream().map(t -> new TaskCreateDto(t.getMachine().getName(),
                t.getNetworkPath(), t.getScanInterval(), t.isSingleFile(), t.isActive())).collect(Collectors.toList()));
    }

    @PostMapping
    // @todo refatorar, controller está sabendo de mais da implementação
    public ResponseEntity<Task> create(@RequestBody TaskCreateDto taskToCreate) {
        Machine machine = machineService.listOne(taskToCreate.getMachine());

        Task taskCreated = taskService.create(new Task(machine, taskToCreate.getNetworkPath(),
                taskToCreate.getScanInterval(), taskToCreate.isSingleFile(), taskToCreate.isActive(), taskToCreate.getFileFilter()));

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

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable String taskId, @Valid @RequestBody TaskUpdateDto taskToUpdate) {
        Task task = taskService.update(taskId, taskToUpdate);

        return ResponseEntity.ok(new TaskResponseDto(task));
    }

    @GetMapping("/{taskId}/scripts")
    public ResponseEntity<List<ScriptResponseDto>> list(@PathVariable String taskId) {
        List<ScriptResponseDto> scripts = taskService.findScriptsRelatedToTask(taskId).stream().map(ScriptResponseDto::new).toList();

        return ResponseEntity.ok(scripts);
    }
}

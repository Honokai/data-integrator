package dev.honokai.data_integrator_backend.application.controllers;

import dev.honokai.data_integrator_backend.application.converter.GenericConverter;
import dev.honokai.data_integrator_backend.application.dtos.machine.MachineIndexDto;
import dev.honokai.data_integrator_backend.application.dtos.machine.MachinePostDto;
import dev.honokai.data_integrator_backend.application.dtos.task.TaskResponseDto;
import dev.honokai.data_integrator_backend.application.services.MachineService;
import dev.honokai.data_integrator_backend.application.services.TaskService;
import dev.honokai.data_integrator_backend.domain.entities.Machine;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/machines")
public class MachineController {
    private final MachineService machineService;
    private final TaskService taskService;

    public MachineController(MachineService machineService, TaskService taskService) {
        this.machineService = machineService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<MachineIndexDto>> index() {
        return ResponseEntity.ok(machineService.listAll().stream().map(machine -> new MachineIndexDto(machine))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{machineId}")
    public ResponseEntity<MachineIndexDto> edit(@PathVariable(name = "machineId") String machineId) {
        Machine machine = machineService.listOne(machineId);

        System.out.println(machine);

        return ResponseEntity.ok(new MachineIndexDto(machine));
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

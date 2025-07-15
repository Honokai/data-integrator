package dev.honokai.data_integrator_backend.application.services;

import dev.honokai.data_integrator_backend.application.dtos.TaskUpdateDto;
import dev.honokai.data_integrator_backend.domain.entities.Script;
import dev.honokai.data_integrator_backend.domain.entities.Task;
import dev.honokai.data_integrator_backend.infrastructure.repositories.TaskRepository;
import dev.honokai.data_integrator_backend.infrastructure.services.SchedulerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MachineService machineService;

    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private ScriptService scriptService;

//	public void onApplicationStart() {
//		List<BaseTask> tasks = taskRepository.findAllActive().stream().map(t -> new ScanTask(t))
//				.collect(Collectors.toList());
//
//		for (int index = 0; index < tasks.size(); index++) {
//			BaseTask scheduledTask = tasks.get(index);
//			schedulerService.addScheduledTask(scheduledTask.getTask().getId(), scheduledTask);
//		}
//	}

    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> listAllActive() {
        return taskRepository.findAllActive();
    }

    public Task update(String taskId, TaskUpdateDto taskToUpdate) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setActive(taskToUpdate.isActive());
        task.setFileFilter(taskToUpdate.getFileFilter());
        task.setNetworkPath(taskToUpdate.getNetworkPath());
        task.setSingleFile(taskToUpdate.isSingleFile());
        task.setScanInterval(taskToUpdate.getScanInterval());

        taskRepository.save(task);
        System.out.println(task.isSingleFile());

        if (task.isActive()
                && task.getScripts().stream().anyMatch(Script::isActive)) {
            System.out.println("Entrou dentro da condição");

//            schedulerService.addScheduledTask(taskUpdated.getId(), new ScanTask(taskUpdated));
        }

        return task;
    }

    public boolean stop(String taskIdentifier) {
        return schedulerService.stopScheduledTask(taskIdentifier);
    }

    public List<Task> findTasksRelatedToMachine(String machineId) {
        return taskRepository.findByMachineId(machineId);
    }

    public Optional<Task> findById(String taskIdentifier) {
        return taskRepository.findById(taskIdentifier);
    }

    public List<Script> findScriptsRelatedToTask(String taskId) {
        return scriptService.findScriptsFromTask(taskId);
    }
}

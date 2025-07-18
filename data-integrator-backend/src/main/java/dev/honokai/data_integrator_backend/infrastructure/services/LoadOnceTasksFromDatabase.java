package dev.honokai.data_integrator_backend.infrastructure.services;

import dev.honokai.data_integrator_backend.application.services.TaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class LoadOnceTasksFromDatabase {
    private final TaskService taskService;

    public LoadOnceTasksFromDatabase(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostConstruct
    public void init() {
        taskService.registerTasksToRun();
    }
}

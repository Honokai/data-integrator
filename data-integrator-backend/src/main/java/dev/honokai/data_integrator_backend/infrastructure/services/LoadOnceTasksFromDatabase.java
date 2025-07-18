package dev.honokai.data_integrator_backend.infrastructure.services;

import dev.honokai.data_integrator_backend.application.services.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadOnceTasksFromDatabase {
    @Autowired
    private TaskService taskService;

    @Autowired
    private SchedulerService schedulerService;

    @PostConstruct
    @Transactional
    public void init() {
        taskService.onApplicationStart();
    }
}

package dev.honokai.data_integrator_backend.infrastructure.services;

import dev.honokai.data_integrator_backend.application.services.TaskService;
import dev.honokai.data_integrator_backend.infrastructure.tasksdefinition.BaseTask;
import dev.honokai.data_integrator_backend.infrastructure.tasksdefinition.ScanTask;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoadOnceTasksFromDatabase {
    @Autowired
    private TaskService taskService;

    @Autowired
    private SchedulerService schedulerService;

    @PostConstruct
    @Transactional
    public void init() {
        List<BaseTask> tasks = taskService.listAllActive().stream().map(t -> new ScanTask(t))
                .collect(Collectors.toList());

        for (int index = 0; index < tasks.size(); index++) {
            BaseTask scheduledTask = tasks.get(index);
            schedulerService.addScheduledTask(scheduledTask.getTask().getId(), scheduledTask);
        }
    }
}

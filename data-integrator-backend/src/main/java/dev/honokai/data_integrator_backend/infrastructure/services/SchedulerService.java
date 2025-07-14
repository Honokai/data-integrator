package dev.honokai.data_integrator_backend.infrastructure.services;

import dev.honokai.data_integrator_backend.infrastructure.tasksdefinition.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
    @Autowired
    private ScheduledTasksMonitorService scheduledTasksMonitorService;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    public boolean addScheduledTask(String taskIdentifier, BaseTask scheduleTask) {
        beanFactory.autowireBean(scheduleTask);
        scheduledTasksMonitorService.addScheduledTask(taskIdentifier,
                taskScheduler.scheduleWithFixedDelay(scheduleTask, scheduleTask.getScanIntervalDurationInSeconds()));

        return true;
    }

    public boolean stopScheduledTask(String taskIdentifier) {
        scheduledTasksMonitorService.stopScheduledTask(taskIdentifier);

        return true;
    }
}

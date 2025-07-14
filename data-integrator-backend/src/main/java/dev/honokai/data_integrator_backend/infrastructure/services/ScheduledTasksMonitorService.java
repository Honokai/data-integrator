package dev.honokai.data_integrator_backend.infrastructure.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduledTasksMonitorService {
    private Map<String, ScheduledFuture<?>> runningTasks = new ConcurrentHashMap<>();

    public void addScheduledTask(String taskIdentifier, ScheduledFuture<?> scheduledTask) {
        this.runningTasks.put(taskIdentifier, scheduledTask);
    }

    public boolean stopScheduledTask(String taskIdentifier) {
        ScheduledFuture<?> task = runningTasks.get(taskIdentifier);

        if (task == null) {
            return false;
        }

        System.out.println("Cancelling task");

        task.cancel(true);

        runningTasks.remove(taskIdentifier);

        return true;
    }
}

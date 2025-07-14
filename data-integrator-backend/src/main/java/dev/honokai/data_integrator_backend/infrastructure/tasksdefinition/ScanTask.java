package dev.honokai.data_integrator_backend.infrastructure.tasksdefinition;

import dev.honokai.data_integrator_backend.application.services.JobService;
import dev.honokai.data_integrator_backend.domain.entities.Job;
import dev.honokai.data_integrator_backend.domain.entities.Task;
import dev.honokai.data_integrator_backend.domain.events.FileFoundForProcessingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
public class ScanTask extends BaseTask {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private JobService jobService;

    public ScanTask(Task task) {
        super(task);
    }

    public ScanTask() {
    }

    @Override
    public void run() {
        Task task = getTask();
        String machineName = task.getMachine().getName();

        String fullPath = String.format("\\\\%s\\%s", machineName, task.getNetworkPath());

        System.out.printf("Machine: %s | Path: %s | RUN: %s%n", machineName, fullPath, new Date());

        var files = new File(fullPath).listFiles();

        if (files == null)
            return;

        for (int index = 0; index < files.length; index++) {
            String keyName = String.format("%s-%s", task.getId(), files[index].getAbsolutePath());

            if (files[index].isFile()) {
                if (redisTemplate.opsForValue().get(keyName) == null) {
                    redisTemplate.opsForValue().set(keyName, files[index].getAbsolutePath());
                    Job job = new Job(task, files[index].getPath(), "T");

                    try {
                        job = jobService.create(job);
                    } catch (DataIntegrityViolationException ex) {
                        System.out.println("Job already present in the database");
                    }

                    publisher.publishEvent(new FileFoundForProcessingEvent(this, job));
                }

                System.out.printf("%s - %s - %s%n", task.getMachine().getName(), files[index].getName(), new Date());
            }
        }

    }
}

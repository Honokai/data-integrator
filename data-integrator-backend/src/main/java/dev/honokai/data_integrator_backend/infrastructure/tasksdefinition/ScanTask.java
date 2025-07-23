package dev.honokai.data_integrator_backend.infrastructure.tasksdefinition;

import dev.honokai.data_integrator_backend.application.services.JobService;
import dev.honokai.data_integrator_backend.domain.entities.Job;
import dev.honokai.data_integrator_backend.domain.entities.Task;
import dev.honokai.data_integrator_backend.domain.events.FileFoundForProcessingEvent;
import dev.honokai.data_integrator_backend.domain.interfaces.FileSourceStrategy;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;

@Component
@Scope("prototype")
public class ScanTask extends BaseTask {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ApplicationEventPublisher publisher;
    private final JobService jobService;
    private FileSourceStrategy strategy;

    public ScanTask(RedisTemplate<String, Object> redisTemplate, ApplicationEventPublisher publisher, JobService jobService) {
        this.redisTemplate = redisTemplate;
        this.publisher = publisher;
        this.jobService = jobService;
    }

    public FileSourceStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(FileSourceStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void run() {
        Task task = getTask();

        List<File> files = strategy.findFiles(task);

        if (files == null || files.isEmpty()) {
            return;
        }

        String machineName = task.getMachine().getName();

        String fullPath = String.format("\\\\%s\\%s", machineName, task.getNetworkPath());

        System.out.printf("Machine: %s | Path: %s | RUN: %s%n", machineName, fullPath, new Date());

        for (File file : files) {
            String keyName = String.format("%s-%s", task.getId(), file.getAbsolutePath());

            if (file.isFile()) {
                if (redisTemplate.opsForValue().get(keyName) == null) {
                    redisTemplate.opsForValue().set(keyName, file.getAbsolutePath());
                    Job job = new Job(task, file.getPath(), "T");

                    try {
                        job = jobService.create(job);
                    } catch (DataIntegrityViolationException ex) {
                        System.out.println("Job already present in the database");
                    }

                    publisher.publishEvent(new FileFoundForProcessingEvent(this, job));
                }

                System.out.printf("%s - %s - %s%n", task.getMachine().getName(), file.getName(), new Date());
            }
        }
    }
}

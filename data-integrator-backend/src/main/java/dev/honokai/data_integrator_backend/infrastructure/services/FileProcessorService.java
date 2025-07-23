package dev.honokai.data_integrator_backend.infrastructure.services;

import dev.honokai.data_integrator_backend.application.services.JobService;
import dev.honokai.data_integrator_backend.application.services.ScriptService;
import dev.honokai.data_integrator_backend.application.services.TaskService;
import dev.honokai.data_integrator_backend.domain.entities.Job;
import dev.honokai.data_integrator_backend.domain.entities.Script;
import dev.honokai.data_integrator_backend.domain.enums.JobStatus;
import dev.honokai.data_integrator_backend.domain.events.FileProcessedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileProcessorService {
    @Autowired
    private JobService jobService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ScriptService scriptService;

    public void updateJobToProcessing(String taskId, String networkPath) {
        Optional<Job> job = jobService.getJobByTaskIdAndPath(taskId, networkPath);

        if (job.isEmpty()) {
            throw new RuntimeException("Não foi possível processar o arquivo");
        }

        job.get().setStatus(JobStatus.PROCESSING);

        jobService.updateJob(job.get());
    }

    public void process(String taskId, String networkPath) throws IOException {
        Optional<Script> script = scriptService.getActiveScriptForTask(taskId);
        Optional<Job> job = jobService.findByTaskIdAndPathAndStatus(taskId, networkPath, JobStatus.PROCESSING);

        for (int index = 0; index < 3000000; index++) {
//			if (index % 10000 == 0) {
            System.out.println(index);
//			}
        }
        if (script.isEmpty() || job.isEmpty()) {
            throw new RuntimeException("Script or Job is missing this request is not processable");
        }

        File file = new File(job.get().getPath());

        String pathToMoveFile = String.format("%s\\%s", createProcessedFolder(file.getParent()), file.getName());
        Path destinationPath = Paths.get(pathToMoveFile);

        CodeRunnerService.run(script.get().getInstruction(), file.getAbsolutePath());

        Files.move(Paths.get(file.getAbsolutePath()), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        job.get().setStatus(JobStatus.COMPLETED);

        jobService.updateJob(job.get());

        done(job.get());
    }

    private String createProcessedFolder(String file) {
        File processedFolder = new File(file + "\\processed");

        if (!processedFolder.exists()) {
            processedFolder.mkdirs();
        }

        return processedFolder.getAbsolutePath();
    }

    private void done(Job job) {
        publisher.publishEvent(new FileProcessedEvent(job));
    }
}

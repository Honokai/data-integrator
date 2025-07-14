package dev.honokai.data_integrator_backend.domain.entities;

import dev.honokai.data_integrator_backend.domain.enums.JobStatus;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"machine", "path", "fileMetadata"})})
public class Job extends Base {
    @ManyToOne()
    private Task task;

    private String path;

    private String fileMetadata;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    public Job(Task task, String path, String fileMetadata) {
        super();
        this.task = task;
        this.path = path;
        this.fileMetadata = fileMetadata;
        this.status = JobStatus.PENDING;
    }

    public Job() {
        // TODO Auto-generated constructor stub
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(String fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileMetada() {
        return fileMetadata;
    }

    public void setFileMetada(String fileMetadata) {
        this.fileMetadata = fileMetadata;
    }
}

package dev.honokai.data_integrator_backend.application.dtos.task;

import dev.honokai.data_integrator_backend.domain.entities.FileFilter;
import dev.honokai.data_integrator_backend.domain.entities.Task;

public class TaskResponseDto {
    private String id;

    private String networkPath;

    private boolean singleFile = false;

    private int scanInterval = 0;

    private boolean active;

    private FileFilter fileFilter;

    public TaskResponseDto() {
        // TODO Auto-generated constructor stub
    }

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.networkPath = task.getNetworkPath();
        this.active = task.isActive();
        this.scanInterval = task.getScanInterval();
        this.fileFilter = task.getFileFilter();
        this.singleFile = task.isSingleFile();
    }

    public TaskResponseDto(String id, String networkPath, boolean singleFile, int scanInterval, boolean active, FileFilter fileFilter) {
        this.id = id;
        this.networkPath = networkPath;
        this.singleFile = singleFile;
        this.scanInterval = scanInterval;
        this.active = active;
        this.fileFilter = fileFilter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetworkPath() {
        return networkPath;
    }

    public void setNetworkPath(String networkPath) {
        this.networkPath = networkPath;
    }

    public boolean isSingleFile() {
        return singleFile;
    }

    public void setSingleFile(boolean singleFile) {
        this.singleFile = singleFile;
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public FileFilter getFileFilter() {
        return fileFilter;
    }

    public void setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }
}

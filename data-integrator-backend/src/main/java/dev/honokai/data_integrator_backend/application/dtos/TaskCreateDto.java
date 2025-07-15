package dev.honokai.data_integrator_backend.application.dtos;

import dev.honokai.data_integrator_backend.domain.entities.FileFilter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskCreateDto {
    @NotBlank
    private String machine;

    @NotBlank
    private String networkPath;

    @Min(value = 5L)
    private int scanInterval;

    private boolean singleFile;

    private boolean active;

    @NotNull
    private FileFilter fileFilter;

    public TaskCreateDto(String machine, String networkPath, int scanInterval, boolean singleFile, boolean active) {
        this.machine = machine;
        this.networkPath = networkPath;
        this.scanInterval = scanInterval;
        this.singleFile = singleFile;
        this.active = active;
    }

    public TaskCreateDto() {
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getNetworkPath() {
        return networkPath;
    }

    public void setNetworkPath(String networkPath) {
        this.networkPath = networkPath;
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public boolean isSingleFile() {
        return singleFile;
    }

    public void setSingleFile(boolean singleFile) {
        this.singleFile = singleFile;
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

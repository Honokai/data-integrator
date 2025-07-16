package dev.honokai.data_integrator_backend.application.dtos.machine;

import dev.honokai.data_integrator_backend.application.dtos.task.TaskResponseDto;
import dev.honokai.data_integrator_backend.domain.entities.Machine;

import java.util.Set;
import java.util.stream.Collectors;

public class MachineGetDto {
    private String id;
    private String name;

    private String ip;

    private boolean active;

    private Set<TaskResponseDto> tasks;

    public MachineGetDto() {
        // TODO Auto-generated constructor stub
    }

    public MachineGetDto(String name, String ip, boolean active) {
        super();
        this.name = name;
        this.ip = ip;
        this.active = active;
    }

    public MachineGetDto(Machine machine) {
        this.id = machine.getId();
        this.name = machine.getName();
        this.ip = machine.getIp();
        this.active = machine.isActive();
        this.tasks = machine.getTasks().stream().map(task -> new TaskResponseDto(task)).collect(Collectors.toSet());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<TaskResponseDto> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskResponseDto> tasks) {
        this.tasks = tasks;
    }

}

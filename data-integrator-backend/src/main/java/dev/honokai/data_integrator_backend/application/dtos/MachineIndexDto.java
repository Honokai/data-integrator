package dev.honokai.data_integrator_backend.application.dtos;

import dev.honokai.data_integrator_backend.domain.entities.Machine;

/**
 *
 */
public class MachineIndexDto {
    private String id;
    private String name;

    private String ip;

    private boolean active;

    public MachineIndexDto() {
        // TODO Auto-generated constructor stub
    }

    public MachineIndexDto(String name, String ip, boolean active) {
        super();
        this.name = name;
        this.ip = ip;
        this.active = active;
    }

    public MachineIndexDto(Machine machine) {
        this.id = machine.getId();
        this.name = machine.getName();
        this.ip = machine.getIp();
        this.active = machine.isActive();
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
}

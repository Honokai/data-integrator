package dev.honokai.data_integrator_backend.application.dtos.script;

import dev.honokai.data_integrator_backend.domain.entities.Script;

import java.math.BigDecimal;

public class ScriptResponseDto {
    private String id;
    private BigDecimal version;

    private String instruction;

    private boolean active;

    private String task;

    public ScriptResponseDto() {
    }

    public ScriptResponseDto(Script script) {
        this.id = script.getId();
        this.version = script.getVersion();
        this.instruction = script.getInstruction();
        this.active = script.isActive();
        this.task = script.getTask().getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}

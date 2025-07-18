package dev.honokai.data_integrator_backend.application.dtos.script;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ScriptPostDto {
    @NotNull
    private BigDecimal version;

    @NotNull
    private String instruction;

    @NotNull
    private String task;

    private boolean active;

    public ScriptPostDto() {
    }

    public ScriptPostDto(BigDecimal version, String instruction, String task, boolean active) {
        this.version = version;
        this.instruction = instruction;
        this.task = task;
        this.active = active;
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

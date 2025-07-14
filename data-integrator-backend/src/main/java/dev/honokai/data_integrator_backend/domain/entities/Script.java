package dev.honokai.data_integrator_backend.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class Script extends Base {
	@Column(nullable = false)
	private BigDecimal version;

	@Column(columnDefinition = "longtext", nullable = false)
	private String instruction;

	@ManyToOne
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;

	private boolean active;

	public Script() {
		// TODO Auto-generated constructor stub
	}

	public Script(BigDecimal version, String instruction, Task task, boolean active) {
		this.version = version;
		this.instruction = instruction;
		this.active = active;
		this.task = task;
	}

	public Script(String id, BigDecimal version, String instruction, Task task, boolean active) {
		super(id);
		this.version = version;
		this.instruction = instruction;
		this.active = active;
		this.task = task;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
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

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}

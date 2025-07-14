package dev.honokai.data_integrator_backend.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Machine extends Base {
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String ip;

	@Column(nullable = false)
	private boolean active;

	@OneToMany(mappedBy = "machine")
	private Set<Task> tasks = new HashSet<>();

	public Machine() {
	}

	public Machine(String name, String ip, boolean active, Set<Task> tasks) {
		super();
		this.name = name;
		this.ip = ip;
		this.active = active;
		this.tasks = tasks;
	}

	public Machine(String id, String name, String ip, boolean active, Set<Task> tasks) {
		super(id);
		this.name = name;
		this.ip = ip;
		this.active = active;
		this.tasks = tasks;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
}

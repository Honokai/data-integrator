package dev.honokai.data_integrator_backend.domain.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Task extends Base {
	@Column(nullable = false)
	private String networkPath;

	@Column(columnDefinition = "bool")
	private boolean singleFile = false;

	private int scanInterval = 0;

	@ManyToOne
	@JoinColumn(name = "machine_id", nullable = false)
	private Machine machine;

	@Embedded
	private FileFilter fileFilter;

	private boolean active;

	public Optional<Script> getActiveScript() {
		return this.scripts.stream().filter(Script::isActive).findFirst();
	}

	@OneToMany(mappedBy = "task")
	private Set<Script> scripts = new HashSet<>();

	public Task() {
	}

	public Task(Machine machine, String networkPath, int scanInterval, boolean singleFile, boolean active) {
		this.machine = machine;
		this.networkPath = networkPath;
		this.scanInterval = scanInterval;
		this.singleFile = singleFile;
		this.active = active;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public String getNetworkPath() {
		return networkPath;
	}

	public void setNetworkPath(String networkPath) {
		this.networkPath = networkPath;
	}

	public Set<Script> getScripts() {
		return scripts;
	}

	public void setScripts(Set<Script> scripts) {
		this.scripts = scripts;
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

	public FileFilter getFileFilter() {
		return fileFilter;
	}

	public void setFileFilter(FileFilter fileFilter) {
		this.fileFilter = fileFilter;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}

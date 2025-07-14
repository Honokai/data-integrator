package dev.honokai.data_integrator_backend.application.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class TaskCreateDto {
	@NotBlank
	private String machine;

//	@Pattern(regexp = "(?<![\\d.])(?:(?:[1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(?:[1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])(?![\\d.])")
//	private String ip;

	@NotBlank
	private String networkPath;

	@Min(value = 5L)
	private int scanInterval;

	private boolean singleFile = false;

	private boolean active = false;

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
}

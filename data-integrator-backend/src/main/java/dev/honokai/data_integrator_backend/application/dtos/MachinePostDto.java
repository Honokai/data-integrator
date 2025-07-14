package dev.monitor.tasks.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MachinePostDto {
	@NotNull
	@Size(min = 5)
	private String name;

	@NotNull
	@Pattern(regexp = "(?<![\\d.])(?:(?:[1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(?:[1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])(?![\\d.])")
	private String ip;

	private boolean active;

	public MachinePostDto(String name, String ip, boolean active) {
		this.name = name;
		this.ip = ip;
		this.active = active;
	}

	public MachinePostDto() {
		// TODO Auto-generated constructor stub
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
}

package dev.honokai.data_integrator_backend.application.dtos;

public class JobForQueue {
	private String id;

	private String taskId;
	private String path;

	public JobForQueue() {
		// TODO Auto-generated constructor stub
	}

	public JobForQueue(String id, String taskId, String path) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.path = path;
	}

	public JobForQueue(String taskId, String path) {
		super();
		this.taskId = taskId;
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}

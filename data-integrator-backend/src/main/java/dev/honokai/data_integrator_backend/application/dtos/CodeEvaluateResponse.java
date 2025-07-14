package dev.honokai.data_integrator_backend.application.dtos;

public class CodeEvaluateResponse {
	private String errorMessage;
	private boolean success;

	public CodeEvaluateResponse() {
		// TODO Auto-generated constructor stub
	}

	public CodeEvaluateResponse(String errorMessage, boolean success) {
		this.errorMessage = errorMessage;
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}

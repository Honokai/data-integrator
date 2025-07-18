package dev.honokai.data_integrator_backend.application.dtos;

public class CodeEvaluateResponse {
    private String message;
    private boolean success;

    public CodeEvaluateResponse() {
        // TODO Auto-generated constructor stub
    }

    public CodeEvaluateResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

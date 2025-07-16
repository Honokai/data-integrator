package dev.honokai.data_integrator_backend.application.dtos;

import jakarta.validation.constraints.NotBlank;

public class TestCodeDto {
    @NotBlank
    private String code;
    @NotBlank
    private String contentToTestAgainst;

    public TestCodeDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContentToTestAgainst() {
        return contentToTestAgainst;
    }

    public void setContentToTestAgainst(String contentToTestAgainst) {
        this.contentToTestAgainst = contentToTestAgainst;
    }
}

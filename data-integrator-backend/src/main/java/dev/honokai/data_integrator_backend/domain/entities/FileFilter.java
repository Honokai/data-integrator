package dev.honokai.data_integrator_backend.domain.entities;

import dev.honokai.data_integrator_backend.domain.enums.FileFilterType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class FileFilter {
    @Column(nullable = false)
    private String pattern;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileFilterType type;

    public FileFilter() {
    }

    public FileFilter(String pattern, FileFilterType type) {
        this.pattern = pattern;
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public FileFilterType getType() {
        return type;
    }

    public void setType(FileFilterType type) {
        this.type = type;
    }
}

package dev.honokai.data_integrator_backend.domain.entities;

import dev.honokai.data_integrator_backend.domain.enums.IntegrationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Integrator extends Base {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private IntegrationType integrationType = IntegrationType.REST_API;

    @OneToOne
    private Task task;

    @Column(columnDefinition = "json")
    private String config;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public IntegrationType getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(IntegrationType integrationType) {
        this.integrationType = integrationType;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}

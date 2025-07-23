package dev.honokai.data_integrator_backend.application.listeners;

import dev.honokai.data_integrator_backend.domain.entities.RestApiIntegrator;
import dev.honokai.data_integrator_backend.domain.events.FileProcessedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FileProcessedEventListener {
    @EventListener
    public void handleFileProcessedEvent(FileProcessedEvent event) {
        RestApiIntegrator restApiIntegrator = new RestApiIntegrator();
        restApiIntegrator.executeIntegration();
    }
}

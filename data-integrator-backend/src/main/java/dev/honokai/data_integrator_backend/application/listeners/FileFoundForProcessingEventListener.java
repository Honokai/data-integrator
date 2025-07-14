package dev.honokai.data_integrator_backend.application.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.honokai.data_integrator_backend.domain.events.FileFoundForProcessingEvent;
import dev.honokai.data_integrator_backend.infrastructure.messaging.FileForProcessingProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FileFoundForProcessingEventListener {
    @Autowired
    private FileForProcessingProducer fileForProcessingProducer;

    @EventListener
    public void handleFileToQueue(FileFoundForProcessingEvent eventTriggered) {
        try {
            fileForProcessingProducer.sendFileToProcessingQueue(eventTriggered.getJob());
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException("Não foi possível processar a entidade");
        }

    }
}

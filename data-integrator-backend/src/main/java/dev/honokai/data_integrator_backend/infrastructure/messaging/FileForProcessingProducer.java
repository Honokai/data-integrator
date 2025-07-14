package dev.honokai.data_integrator_backend.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.honokai.data_integrator_backend.application.dtos.JobForQueue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileForProcessingProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendFileToProcessingQueue(JobForQueue job) throws JsonProcessingException {
        amqpTemplate.convertAndSend("file-for-processing-exchange", "file-for-processing",
                objectMapper.writeValueAsString(job));
    }
}

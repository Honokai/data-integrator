package dev.honokai.data_integrator_backend.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import dev.honokai.data_integrator_backend.application.dtos.JobForQueue;
import dev.honokai.data_integrator_backend.infrastructure.services.FileProcessorService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileForProcessingConsumer {
    @Autowired
    private FileProcessorService fileProcessorService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = {
            "file-for-processing"}, containerFactory = "rabbitListenerContainerFactory", ackMode = "MANUAL")
    public void receive(Message message, Channel channel) {
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();

        JobForQueue job;

        try {
            job = objectMapper.readValue(message.getBody(), JobForQueue.class);

            fileProcessorService.updateJobToProcessing(job.getTaskId(), job.getPath());

            fileProcessorService.process(job.getTaskId(), job.getPath());

            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
//			channel.basicReject(deliveryTag, true);
            e.printStackTrace();
            throw new RuntimeException("Unprocessed");
        }
    }

}

package dev.honokai.data_integrator_backend.infrastructure.configurations;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class RabbitConfig {
    @Bean
    public ThreadPoolTaskExecutor rabbitExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(3);
        exec.setMaxPoolSize(4);
        exec.setQueueCapacity(10); // backlog máximo na aplicação
        exec.setThreadNamePrefix("file-proc-");
        exec.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        exec.initialize();
        return exec;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               ThreadPoolTaskExecutor rabbitExecutor) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setTaskExecutor(rabbitExecutor);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(4);
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return factory;
    }
}

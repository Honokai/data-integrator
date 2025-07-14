package dev.honokai.data_integrator_backend;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRabbit
@EnableScheduling
@SpringBootApplication
public class DataIntegratorBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataIntegratorBackendApplication.class, args);
	}

}

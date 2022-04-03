package org.architecturemining.trip.trip;

import org.architecturemining.trip.trip.service.InputController;
import org.architecturemining.trip.trip.service.OutputController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class TripApplication {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Communicator getCommunicator() {
		return new Communicator();
	}

	public static void main(String[] args) {
		SpringApplication.run(TripApplication.class, args);
	}

	public class Communicator {

		@Autowired
		private InputController inputController;

		@Autowired
		private OutputController outputController;

		public InputController getInputController() {
			return inputController;
		}

		public OutputController getOutputController() {
			return outputController;
		}

	}
}

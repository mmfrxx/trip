package org.architecturemining.trip.trip.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OutputController {
    private Logger logger = LoggerFactory.getLogger(OutputController.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InputController inputController;

    @Autowired
    private DiscoveryClient discoveryClient;

    protected boolean isServiceAvailable(String name) {
        return (discoveryClient.getInstances(name).size() > 0);
    }

    public boolean isCheckServiceAvailable() {
        return isServiceAvailable("check-service");
    }

    public Boolean checkInWithAnonTicket(String client) {
        if (isCheckServiceAvailable()) {
            logger.info("Received check-in request with anonymous ticket from client: " + client
            + ". I will pass the request to our check-in/out management system.");
            String response = restTemplate.getForObject("http://check-service/anonymous/check-in/" + client, String.class );
        } else {
            logger.error("Please run check-service!!!");
        }
        return true;
    }

}

package com.tripsystem.payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class PaymentService {
    private Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    protected boolean isServiceAvailable(String name) {
        return (discoveryClient.getInstances(name).size() > 0);
    }

    public boolean isUserServiceAvailable() {
        return isServiceAvailable("user-service");
    }

    @RequestMapping("/pay/{client}/{type}")
    public String processPayment(@PathVariable("client") String client,
                                 @PathVariable("type") String type) {
        logger.info("Passing card credentials of client " + client + " to PSP.");
        if (!isTransactionSuccessful()){
            logger.error("Could not bind card to account for client " + client);
            return client;
        }
        logger.info("Bound card to account for client " + client + ". Sending data to user-service to make an update.");
        if(isUserServiceAvailable()){
            String response = restTemplate.getForObject(
                    "http://user-service/update/" + client + "/" + type, String.class);
        } else {
            logger.error("Please run user-service!!!!");
        }
        logger.info("Sending ");
        return client;
    }

    private Boolean isTransactionSuccessful(){
        Random rand = new Random();
        return rand.nextBoolean();
    }

}

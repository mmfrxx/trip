package com.tripsystem.checks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CheckService {
    private Logger logger = LoggerFactory.getLogger(CheckService.class);
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

    public boolean isPaymentServiceAvailable() {
        return isServiceAvailable("payment-service");
    }


    @RequestMapping("/check-in/{owner}/{hasEnoughCredit}")
    public String checkIn(@PathVariable("owner") String owner,
                          @PathVariable("hasEnoughCredit") Boolean hasEnoughCredit) {
        if (!isUserServiceAvailable()) {
            logger.error("Please run user-service");
            return owner;
        } else if (!isPaymentServiceAvailable()) {
            logger.error("Please run payment-service");
            return owner;
        }

        if(!hasEnoughCredit) {
            logger.info("Client" + owner + " does not have enough balance, proceeding to top-up");
            String response = restTemplate.getForObject("http://payment-service/top-up/" + owner, String.class);
        }
        logger.info("Client " + owner + " checked-in. I will send update profile request to our user service.");
        String resp = restTemplate.getForObject("http://user-service/check-in/" + owner, String.class);
        return owner;
    }

    @RequestMapping("/check-out/{owner}")
    public String checkOut(@PathVariable("owner") String owner) {
        if (!isUserServiceAvailable()) {
            logger.error("Please run user-service");
            return owner;
        } else if (!isPaymentServiceAvailable()) {
            logger.error("Please run payment-service");
            return owner;
        }

        logger.info("Calling payment service to transfer money to tycoon for client" + owner + ".");
        String response = restTemplate.getForObject("http://payment-service/transfer/" + owner, String.class);
        logger.info("Client " + owner + " checked-out. I will send update profile request to our user service.");
        String resp = restTemplate.getForObject("http://user-service/check-out/" + owner, String.class);
        return owner;
    }

    @RequestMapping("/anonymous/check-in/{owner}")
    public String checkInWithAnonTicket(@PathVariable("owner") String owner) {
        logger.info("Received check-in request with anonymous ticket for client: " + owner + ". I will process it and flag ticket as used.");
        return owner;
    }
}
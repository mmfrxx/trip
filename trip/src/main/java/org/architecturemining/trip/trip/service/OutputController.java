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

    public boolean isProductServiceAvailable() {
        return isServiceAvailable("product-service");
    }

    public boolean isPaymentServiceAvailable() {
        return isServiceAvailable("payment-service");
    }

    public boolean isTycoonServiceAvailable() {
        return isServiceAvailable("tycoon-service");
    }

    public Boolean checkInWithAnonTicket(String client) {
        if (isCheckServiceAvailable()) {
            logger.info(String.format("Received check-in request with anonymous ticket from client: %s. I will pass the request to our check-in/out management system.", client));
            String response = restTemplate.getForObject("http://check-service/anonymous/check-in/" + client, String.class);
        } else {
            logger.error("Please run check-service!!!");
        }
        return true;
    }

    public Boolean checkIn(String client, Boolean hasEnoughCredit) {
        if (isCheckServiceAvailable()) {
            logger.info(String.format("Received check-in request with personal account/card from client: %s. I will pass the request to our check-in/out management system.", client));
            String response = restTemplate.getForObject("http://check-service/check-in/" + client + "/" + hasEnoughCredit, String.class);
        } else {
            logger.error("Please run check-service!!!");
        }
        return true;
    }

    public Boolean checkOut(String client) {
        if (isCheckServiceAvailable()) {
            logger.info(String.format("Received check-out request from client: %s. I will pass the request to our check-in/out management system.", client));
            String response = restTemplate.getForObject("http://check-service/check-out/" + client, String.class);
        } else {
            logger.error("Please run check-service!!!");
        }
        return true;
    }

    public Boolean getProductsList(String productType, String client) {
        if (isProductServiceAvailable()) {
            logger.info(String.format("I will pass %s purchase request to our product management for client: %s", productType, client));
            String response = restTemplate.getForObject(
                    "http://product-service/product-list/" + client + "/" + productType, String.class);
            return true;
        } else {
            logger.error("Please run product-service!!!");
            return false;
        }
    }

    public Boolean payForProduct(String type, String client){
        if (isPaymentServiceAvailable()) {
            logger.info("Initializing payment for client " + client + "to purchase " + type);
            String response = restTemplate.getForObject(
                    "http://payment-service/pay/" + client + "/" + type, String.class);
        } else {
            logger.error("Please run payment-service!!!");
            return false;
        }
        return true;
    }

    public Boolean removeTycoon(String tycoon, String client) {
        if (isTycoonServiceAvailable()) {
            String response = restTemplate.getForObject("http://tycoon-service/remove/" + client + "/" + tycoon, String.class);
        } else {
            logger.error("Please run tycoon-service!!!");
        }
        return true;
    }

    public Boolean addTycoon(String tycoon, String client) {
        if (isTycoonServiceAvailable()) {
            String response = restTemplate.getForObject("http://tycoon-service/add/" + client + "/" + tycoon, String.class);
        } else {
            logger.error("Please run tycoon-service!!!");
        }
        return true;
    }

}

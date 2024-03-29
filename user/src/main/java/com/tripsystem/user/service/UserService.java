package com.tripsystem.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @RequestMapping("/update/{client}/{type}")
    public String makeAnUpdateAfterProductPurchase(@PathVariable("client") String client,
                                 @PathVariable("type") String type) {
        if ("ticket".equals(type)){
            logger.info("Saving anonymous ticket information for client " + client);
        } else {
            logger.info("Updating client " + client + "'s balance");
        }
        return client;
    }

    @RequestMapping("/check-in/{client}")
    public String checkIn(@PathVariable("client") String client) {
        logger.info("Received check-in request from client" + client + ". Updating account information.");
        return client;
    }

    @RequestMapping("/check-out/{client}")
    public String checkOut(@PathVariable("client") String client) {
        logger.info("Received check-out request from client" + client + ". Updating account information.");
        return client;
    }
}

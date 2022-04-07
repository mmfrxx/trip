package com.tripsystem.checks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CheckService {
    private Logger logger = LoggerFactory.getLogger(CheckService.class);
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/anonymous/check-in/{owner}")
    public String checkInWithAnonTicket(@PathVariable("owner") String owner) {
        logger.info("Received check-in request with anonymous ticket for client: " + owner + ". I will process it and flag as used.");
        return owner;
    }
}
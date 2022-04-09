package com.tripsystem.tycoon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TycoonService {
    private Logger logger = LoggerFactory.getLogger(TycoonService.class);

    @RequestMapping("/add/{client}/{tycoon}")
    public String add(@PathVariable("tycoon") String tycoon,
                      @PathVariable("client") String client) {
        logger.info("Updated the system, now, tycoon " + tycoon + " is our partner after request of client " + client);
        return client;
    }

    @RequestMapping("/remove/{client}/{tycoon}")
    public String remove(@PathVariable("tycoon") String tycoon,
                      @PathVariable("client") String client) {
        logger.info("Updated the system, now, tycoon " + tycoon + " is not our partner after request of client " + client);
        return client;
    }
}

package com.example.productMng.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductManagementService {
    private Logger logger = LoggerFactory.getLogger(ProductManagementService.class);

    @RequestMapping("/product-list/{client}/{productType}")
    public String getProductsList(@PathVariable("client") String client,
                                  @PathVariable("productType") String productType) {
        logger.info("Pulling list of products for " + productType + " from tycoons' side and passing it back to client " + client);
        return client;
    }

}

package org.example.controller;

import org.example.model.Tool;
import org.example.service.CheckoutService;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

@RestController
public class StoreController {

    private CheckoutService checkoutService;
    @Autowired
    public StoreController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    @GetMapping("/checkout")
    public String connect(){
        System.out.println("Spring Boot Application Connected.");
        return "hello world";
    }
    @GetMapping("/tool/{code}")
    public Tool getTool(@PathVariable  String code) {
        return checkoutService.getTool(code);
    }
}
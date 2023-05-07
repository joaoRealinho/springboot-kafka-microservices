package com.example.stockservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {

    private final StreamsBuilderFactoryBean factoryBean;


}

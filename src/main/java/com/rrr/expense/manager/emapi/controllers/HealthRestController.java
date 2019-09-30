package com.rrr.expense.manager.emapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class HealthRestController {

    @GetMapping(value = "/health")
    public String health() {
        return "Service is Up & Running. " + Instant.now().toString();
    }
}

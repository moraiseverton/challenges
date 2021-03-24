package ca.morais.everton.todolist.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(value = "/health")
public class HealthCheckController {

    @GetMapping
    public Instant index() {
        return Instant.now();
    }
}

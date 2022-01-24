package com.blueprint.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health2")
    public String vitality(){
        return "200 OK";
    }
}
package com.blueprint.vitality;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VitalityController {

    @GetMapping("/vitality")
    public String vitality(){
        return "200 OK";
    }
}

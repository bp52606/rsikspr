package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class CheckHealth {

    @GetMapping("/health")

    public String healthChecker(){
        return "OK";
    }


}

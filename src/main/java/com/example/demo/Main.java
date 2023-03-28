package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class Main  {

    @GetMapping("/health")

    public String checkHealth(){
        return "OK";
    }
    /*@Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up()
                .withDetail("workingFine","OK");
    }*/
}

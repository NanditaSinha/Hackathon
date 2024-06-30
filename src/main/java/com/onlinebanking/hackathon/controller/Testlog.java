package com.onlinebanking.hackathon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testlog {

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

}

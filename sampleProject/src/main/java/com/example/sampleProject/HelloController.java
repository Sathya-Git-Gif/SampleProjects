package com.example.sampleProject;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
    public class HelloController {
    @GetMapping("/hello")
        public String hello() {
            return "Hello, World!";
        }
    }

//http://localhost:8080/hello


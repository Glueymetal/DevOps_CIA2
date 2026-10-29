package com.example.demo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MyController{

    @GetMapping("/api/hello")
    public String hello(){
        return "Hello from DevOps Pipeline - v1";
    }

    @GetMapping("api/square")
    public int square(@RequestParam int n){
        return n * n;
    }
}
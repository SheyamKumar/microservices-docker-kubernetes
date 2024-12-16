package com.banking.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

@GetMapping("Hello")
public String get() {
    System.out.println("Hello");
    return "Hi Hello World!";
}
    @GetMapping("Hello1  ")
    public String get1() {
        System.out.println("Hello");
        return "Hi Hello";
    }
}

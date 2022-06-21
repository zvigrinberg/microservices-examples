package org.example.microservicesdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet")
public class HelloController {


    @GetMapping("/{name}")
    public String returnGreeting(@PathVariable String name)
    {
        return "Hello - " + name;
    }


}

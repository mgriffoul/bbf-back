package com.griffoul.mathieu.bbfback.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @GetMapping(value = "/authent")
    public HttpEntity<String> testController(@RequestParam(value = "login") final String login) {
        return ResponseEntity.of(Optional.of("Bonjour "+login));
    }

}

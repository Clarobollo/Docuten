package com.docutem.demo.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/v1/test")
public class Test {

    @Value("${project.version}")
    private String projectVersion;

    @ResponseBody
    @GetMapping("/health")
    public void health(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @ResponseBody
    @GetMapping("/version")
    public String version() {
        return projectVersion;
    }

}

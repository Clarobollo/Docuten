package com.docutem.demo.controller;

import com.docutem.demo.BBDDUtils.bbdd;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.docutem.demo.BBDDUtils.bbdd;
import com.docutem.demo.service.KeysService;

import java.security.Key;
import java.sql.Connection;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("/v1/keys")

public class Keys {
    
    @Autowired
    bbdd bbdd;

    @Autowired
    KeysService keysService;

    @ResponseBody
    @GetMapping(path = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode request(@RequestHeader Map<String, String> headers) throws Exception {
        return keysService.request(headers);
    }
    
}

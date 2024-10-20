package com.docutem.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.docutem.demo.service.KeysService;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@RestController
@RequestMapping("/v1/keys")

public class Keys {

    @Autowired
    KeysService keysService;

    @ResponseBody
    @GetMapping(path = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode request(@RequestHeader Map<String, String> headers) throws Exception {
        return keysService.request(headers);
    }

}

package com.docutem.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.docutem.demo.service.SignsService;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@RestController
@RequestMapping("/v1/sign")
public class Signs {

    @Autowired
    SignsService signsService;

    @ResponseBody
    @PostMapping(path = "/document", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode sign(@RequestHeader Map<String, String> headers, @RequestBody String document) throws JsonProcessingException {
        return signsService.sign(headers, document);
    }

    @ResponseBody
    @PostMapping(path = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode verify(@RequestHeader Map<String, String> headers, @RequestBody JsonNode body) throws JsonProcessingException {
        return signsService.verify(headers, body.get("document").asText(), body.get("signature").asText());
    }
    
}


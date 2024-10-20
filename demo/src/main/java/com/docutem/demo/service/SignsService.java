package com.docutem.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public interface SignsService {
    JsonNode sign(Map<String, String> headers, String document) throws JsonProcessingException;
}

package com.docutem.demo.service.imp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.security.KeyPair;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.docutem.demo.BBDDUtils.bbdd;
import com.docutem.demo.EncryptUtils.Encryption;
import com.docutem.demo.service.KeysService;

@Service
public class KeysServiceMock implements KeysService {

    @Autowired
    bbdd bbdd;

    @Autowired
    Encryption encryption;

    public JsonNode request(Map<String, String> headers) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();
            KeyPair keyPair = encryption.generateKeyPair();
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            bbdd.setKeys(headers.get("user"), headers.get("pass"), privateKey, publicKey);
            response.put("status", "OK");
            return response;
        } catch (SQLException e) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            return response;
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            return response;
        }
    }

}

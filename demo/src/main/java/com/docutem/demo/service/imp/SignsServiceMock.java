package com.docutem.demo.service.imp;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.docutem.demo.BBDDUtils.bbdd;
import com.docutem.demo.EncryptUtils.Encryption;
import com.docutem.demo.service.SignsService;

@Service
public class SignsServiceMock implements SignsService{
    

    @Autowired
    bbdd bbdd;

    @Autowired
    Encryption encryption;

    public JsonNode sign(Map<String, String> headers, String document) throws JsonProcessingException {
        try {

            byte[] documentBytes = Base64.getDecoder().decode(document);
            PrivateKey privateKey = encryption.loadPrivateKey(encryption.decrypt(bbdd.getPrivateKey(headers.get("user"), headers.get("pass"))));

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(documentBytes);

            byte[] digitalSignature = signature.sign();
            String digitalSignatureString = Base64.getEncoder().encodeToString(digitalSignature);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();
            response.put("status", "OK");
            response.put("message", "Document signed successfully");
            response.put("signature", digitalSignatureString);
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

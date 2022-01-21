package com.ktech.starter.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//@Service
public class EncryptionService {

    //@Value("${encryption.client.url}")
    private String encryptionServiceUrl;

    //private HttpClientService httpService;

    //public EncryptionService(@Autowired HttpClientService httpService) {
       // this.httpService = httpService;
   // }

    public String encrypt(String raw) throws IOException, JSONException {

        JSONObject obj = new JSONObject();
        obj.put("job", "E");
        obj.put("raw", raw);

        return send(obj);
    }

    public String decrypt(String encrypted) throws IOException, JSONException {

        JSONObject obj = new JSONObject();
        obj.put("job", "D");
        obj.put("encrypted", encrypted);

        return send(obj);

    }

    public boolean compare(String raw, String encrypted) throws IOException, JSONException {

        JSONObject obj = new JSONObject();
        obj.put("job", "C");
        obj.put("encrypted", encrypted);
        obj.put("raw", raw);
        String ret = send(obj);
        return Boolean.valueOf(ret);
    }

    private String send(JSONObject json) throws JsonProcessingException {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        /*
        try {
            Optional<String> opt = httpService.postForObject(String.class, encryptionServiceUrl, headers, json.toString());
            if (opt.isPresent()) {
                return opt.get();
            } else {
                return "";
            }

        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }

         */
return null;

    }

}
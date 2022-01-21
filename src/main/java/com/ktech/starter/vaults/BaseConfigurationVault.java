package com.ktech.starter.vaults;


import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.*;
import org.apache.commons.lang3.ObjectUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Base64;

public class BaseConfigurationVault {


    private JSONObject json;

    private BaseConfigurationVault() {

    }

    protected BaseConfigurationVault(String secretsName) {
        parseSecret(secretsName);

    }

    private void parseSecret(String secret){

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject) parser.parse(secret);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getSecretByKey(String key) {

        return (String) json.get(key);
    }





}

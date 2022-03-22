package com.ktech.starter.vaults;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BaseConfigurationVault {


    private JSONObject json;

    private BaseConfigurationVault() {

    }

    protected BaseConfigurationVault(String secret) {
        parseSecret(secret);

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

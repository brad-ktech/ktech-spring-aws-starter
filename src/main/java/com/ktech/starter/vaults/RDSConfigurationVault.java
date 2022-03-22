package com.ktech.starter.vaults;

public class RDSConfigurationVault extends BaseConfigurationVault {


    public RDSConfigurationVault(String secret){
        super(secret);

    }


    public String getDBURL() {

        return getSecretByKey("url");
    }

    public String getDBUserName() {

        return getSecretByKey("username");
    }

    public String getDBPassword() {

        return getSecretByKey("password");
    }

    public String getDBDriver() {

        return getSecretByKey("driverClassName");
    }
}

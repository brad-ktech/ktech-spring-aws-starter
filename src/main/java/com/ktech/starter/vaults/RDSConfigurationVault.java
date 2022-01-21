package com.ktech.starter.vaults;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;

public class RDSConfigurationVault extends BaseConfigurationVault {


    public RDSConfigurationVault(String secret){
        super(secret);

    }

    public String getDBHost() {

        return getSecretByKey("host");
    }

    public String getDBURL() {

        return getSecretByKey("url");
    }

    public String getDBPort() {

        return getSecretByKey("port");
    }

    public String getDBName() {

        return getSecretByKey("dbname");
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

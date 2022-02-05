package com.ktech.starter.vaults;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;

public class ClioConfigurationVault extends BaseConfigurationVault {

    public ClioConfigurationVault(String secret){
        super(secret);

    }


    public String getAPITarget(){
        return getSecretByKey("clio.baseTarget");
    }

    public String getAuthToken(){
        return getSecretByKey("clio.authToken");
    }

    public String getClientSecret(){

        return getSecretByKey("clio.clientSecret");
    }

    public String getClientKey(){

        return getSecretByKey("clio.clientKey");
    }



}

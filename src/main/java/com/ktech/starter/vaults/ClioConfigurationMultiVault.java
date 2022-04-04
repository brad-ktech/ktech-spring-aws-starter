package com.ktech.starter.vaults;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.ResettableListIterator;

import java.util.*;


public class ClioConfigurationMultiVault extends BaseConfigurationVault implements ClioVault{


    private ResettableListIterator<String> tokens = null;


    protected ClioConfigurationMultiVault(String secret) {
        super(secret);
    }

    public ClioConfigurationMultiVault(String secret, List<String> tokens){
        super(secret);
        this.tokens = IteratorUtils.loopingListIterator(tokens);
    }

    @Override
    public String getAuthToken() {

       return tokens.next();

    }

    @Override
    public String getAPITarget(){

        return getSecretByKey("clio.baseTarget");
    }

    public String getClientSecret(){

        return getSecretByKey("clio.clientSecret");
    }

    public String getClientKey(){

        return getSecretByKey("clio.clientKey");
    }

    public String getSecretName(){

        return  getSecretByKey("secretName");

    }
}

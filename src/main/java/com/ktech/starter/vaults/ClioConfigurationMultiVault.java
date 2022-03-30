package com.ktech.starter.vaults;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClioConfigurationMultiVault implements ClioVault{

    private static List<ClioConfigurationVault> vaultz = new ArrayList<>();
    private AtomicInteger atomic = new AtomicInteger();

    private ClioConfigurationMultiVault(){

    }

    public ClioConfigurationMultiVault(String... jsonz){

        Arrays.stream(jsonz).forEach(json ->{
            vaultz.add(new ClioConfigurationVault(json));
        });

    }

    private ClioConfigurationVault getNextVault(){

        Integer idx = atomic.getAndIncrement();
        if(idx >= vaultz.size()){
            atomic.set(0);
        }
        return vaultz.get(idx);


    }

    public ClioConfigurationVault getCurrentVault(){

        return vaultz.get(atomic.get());
    }


    public ClioConfigurationVault getVaultBySecret(String secretName){

        Optional<ClioConfigurationVault> opt =  vaultz.stream().filter(vault ->{
            return secretName.equalsIgnoreCase(vault.getSecretName());
        }).findFirst();
        return opt.orElse(null);

    }



    @Override
    public String getAuthToken() {
        return getNextVault().getAuthToken();
    }

    @Override
    public String getAPITarget() {
        return getCurrentVault().getAPITarget();
    }
}

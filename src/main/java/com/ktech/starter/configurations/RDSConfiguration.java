package com.ktech.starter.configurations;


import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.ktech.starter.vaults.RDSConfigurationVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@ConditionalOnProperty(
        value="rds.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class RDSConfiguration extends BaseVaultConfiguration{


    @Value("#{systemEnvironment['RDS_SECRET']}")
    private String rdsSecret;

    @Bean
    public RDSConfigurationVault getRdsConfigurations(@Autowired AWSSecretsManager client){

        String secret = getSecrets(client, rdsSecret.trim());
        return  new RDSConfigurationVault(secret);

    }


}

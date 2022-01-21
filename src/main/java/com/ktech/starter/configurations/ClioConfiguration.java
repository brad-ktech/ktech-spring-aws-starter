package com.ktech.starter.configurations;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.ktech.starter.vaults.ClioConfigurationVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(
        value="clio.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class ClioConfiguration extends BaseVaultConfiguration{

    @Value("#{systemEnvironment['CLIO_SECRET']}")
    private String clioSecret;

    @Bean
    public ClioConfigurationVault getClioConfigurations(@Autowired AWSSecretsManager client){
        String secret = getSecrets(client, clioSecret.trim());
        return new ClioConfigurationVault(secret);

    }
}

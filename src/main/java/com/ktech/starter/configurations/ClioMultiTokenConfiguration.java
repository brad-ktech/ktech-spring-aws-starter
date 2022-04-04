package com.ktech.starter.configurations;


import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.ktech.clioauth.entities.UserToken;
import com.ktech.clioauth.service.ClioAuthorizationService;
import com.ktech.starter.vaults.ClioConfigurationMultiVault;
import com.ktech.starter.vaults.ClioVault;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnProperty(
        value="clio.mulit.enabled",
        havingValue = "true",
        matchIfMissing = false)
public class ClioMultiTokenConfiguration extends BaseVaultConfiguration{

    @Value("#{systemEnvironment['CLIO_SECRET']}")
    private String clioSecret;

    @Autowired
    private ClioAuthorizationService auth;

    @Autowired
    private AWSSecretsManager client;

    private static String TOKEN_USER = "TOKEN_USER";

    @Bean
    public ClioVault getClioConfigurations(){

        ClioConfigurationMultiVault vault = null;
        try {
            String secret = getSecrets(client, clioSecret.trim());
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(secret);
            String tokenUser = (String) json.get(TOKEN_USER);
            List<UserToken> userTokens = auth.getToken(tokenUser);
            List<String> tokens =
                    userTokens.stream()
                            .map(UserToken::getAccessToken)
                            .collect(Collectors.toList());


            vault = new ClioConfigurationMultiVault(secret, tokens);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return vault;


    }






}

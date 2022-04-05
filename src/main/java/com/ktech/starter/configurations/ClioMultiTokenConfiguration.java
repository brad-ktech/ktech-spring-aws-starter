package com.ktech.starter.configurations;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        value="clio.mulit.enabled",
        havingValue = "true",
        matchIfMissing = false)
public class ClioMultiTokenConfiguration extends BaseVaultConfiguration{

    /*
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


*/



}

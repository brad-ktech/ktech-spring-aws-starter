package com.ktech.starter.configurations;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.ktech.starter.clio.security.UnsecureHostnameVerifier;
import com.ktech.starter.clio.security.UnsecureTrustManager;
import com.ktech.starter.vaults.ClioConfigurationVault;
import com.ktech.starter.vaults.ClioVault;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


@Configuration
@ConditionalOnProperty(
        value="clio.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class ClioConfiguration extends BaseVaultConfiguration{

    @Value("#{systemEnvironment['CLIO_SECRET']}")
    private String clioSecret;


    @Bean
    public ClioVault getClioConfigurations(@Autowired AWSSecretsManager client){
        String secret = getSecrets(client, clioSecret.trim());
        return new ClioConfigurationVault(secret);

    }




    @Bean
    public Client getClioClient(@Autowired ClioConfigurationVault vault){

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(OAuth2ClientSupport.feature(vault.getAuthToken()));
        try {
            ClientBuilder builder = ClientBuilder.newBuilder();
            builder.hostnameVerifier(new UnsecureHostnameVerifier());
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            System.setProperty("https.protocols", "TLSv1.2");
            sc.init(new KeyManager[0], UnsecureTrustManager.TrustManagerArray, new java.security.SecureRandom());
            builder.sslContext(sc);
            builder.withConfig(clientConfig);
            return  builder.build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


}

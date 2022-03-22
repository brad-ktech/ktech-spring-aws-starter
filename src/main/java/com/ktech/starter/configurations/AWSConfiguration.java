package com.ktech.starter.configurations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnProperty(
        value="aws.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class AWSConfiguration {

    @Value("#{systemEnvironment['AWS_REGION']}")
    private String region;



    @Bean
    public AWSSecretsManager getSecretManager() {

        return AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    @Bean
    public AmazonSimpleEmailService getSesService() {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    @Bean
    public AmazonS3 amazonS3(){
        return  AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .build();


    }










}

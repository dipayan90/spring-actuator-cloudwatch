package com.kajjoy.spring.devops.cloudwatchmetrics.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private int proxyPort;

    @Bean
    public ClientConfiguration clientConfiguration(){
        ClientConfiguration config = new ClientConfiguration();
        config.setProxyHost(proxyHost);
        config.setProxyPort(proxyPort);
        return config;
    }

    @Bean
    public AWSCredentialsProvider credentialsProvider(){
        return new CredentialProviderChain();
    }

}

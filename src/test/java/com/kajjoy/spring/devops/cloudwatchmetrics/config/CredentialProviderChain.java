package com.kajjoy.spring.devops.cloudwatchmetrics.config;


import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

class CredentialProviderChain extends AWSCredentialsProviderChain{
    CredentialProviderChain() {
        super(new ProfileCredentialsProvider("federated-profile-name"), new ClasspathPropertiesFileCredentialsProvider(), new SystemPropertiesCredentialsProvider(), new EnvironmentVariableCredentialsProvider());
    }
}

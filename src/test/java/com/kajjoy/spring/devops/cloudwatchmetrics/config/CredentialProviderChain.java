package com.kajjoy.spring.devops.cloudwatchmetrics.config;


import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

class CredentialProviderChain extends AWSCredentialsProviderChain{
    CredentialProviderChain() {
        super(new ProfileCredentialsProvider("nordstrom-federated"), new ClasspathPropertiesFileCredentialsProvider(), new SystemPropertiesCredentialsProvider(), new EnvironmentVariableCredentialsProvider());
    }
}

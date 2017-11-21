package com.kajjoy.spring.devops.cloudwatchmetrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("/test.properties")
public class CloudwatchMetricsApplication {

    public static void main(String[] args){
        SpringApplication.run(CloudwatchMetricsApplication.class,args);
    }
}

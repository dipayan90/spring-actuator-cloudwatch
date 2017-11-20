package com.kajjoy.spring.devops.cloudwatchmetrics.publisher;

import com.kajjoy.spring.devops.cloudwatchmetrics.service.CloudWatchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class CloudWatchPublisher {

    @Resource
    private MetricsEndpoint metricsEndpoint;

    @Resource
    private CloudWatchService cloudWatchService;

    /**
     * If none specified all metrics will be published
     */
    @Value("${actuator.metrics.to.publish}")
    private String[] metricsToPublish;

    /**
     * Defaults: Every 10 seconds, can be overwritten using applications properties
     *
     * Examples of acceptable cron expressions can be found: https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
     */
    @Scheduled(cron = "${spring.operational.metrics.cloudwatch.publish.cron:*/10 * * * * *}")
    public void publishCloudWatchMetrics(){
        if(metricsToPublish != null && metricsToPublish.length!=0){
            List<String> keys = Arrays.asList(metricsToPublish);
            metricsEndpoint.invoke().forEach((key, value) -> {
                if(keys.contains(key)){
                    System.out.println(System.currentTimeMillis());
                    System.out.println(key + " : " + value.toString());
                }
            });
        }else {
            metricsEndpoint.invoke().forEach((key, value) -> {
                System.out.println(System.currentTimeMillis());
                System.out.println(key + " : " + value.toString());
            });
        }

    }

}

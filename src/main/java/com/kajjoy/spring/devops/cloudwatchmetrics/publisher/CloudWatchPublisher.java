package com.kajjoy.spring.devops.cloudwatchmetrics.publisher;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.kajjoy.spring.devops.cloudwatchmetrics.service.CloudWatchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Value("${actuator.metrics.units}")
    private String[] meticsUnits;

    @Value("${cloudwatch.namespace}")
    private String cloudWatchNamespace;

    /**
     * Defaults: Every 10 seconds, can be overwritten using applications properties
     *
     * Examples of acceptable cron expressions can be found: https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
     */
    @Scheduled(cron = "${spring.operational.metrics.cloudwatch.publish.cron:*/10 * * * * *}")
    public void publishCloudWatchMetrics(){
        if(metricsToPublish != null && metricsToPublish.length!=0){
            if(metricsToPublish.length != meticsUnits.length){
                throw new IllegalArgumentException("All metrics should have associated units");
            }

            Map<String, StandardUnit> metricsMap = IntStream.range(0, metricsToPublish.length).boxed()
                    .collect(Collectors.toMap(i -> metricsToPublish[i], i -> StandardUnit.valueOf(meticsUnits[i])));

            metricsEndpoint.invoke().forEach((key, value) -> {
                if(metricsMap.containsKey(key)){
                    System.out.println(System.currentTimeMillis());
                    System.out.println(key + " : " + value.toString());
                    publish(key,metricsMap.get(key),metricsMap.get(key).toString(),key,Double.valueOf(value.toString()));
                }
            });
        }else {
            metricsEndpoint.invoke().forEach((key, value) -> {
                System.out.println(System.currentTimeMillis());
                System.out.println(key + " : " + value.toString());
                if(getDefaultUnit(key) != null){
                    publish(key,StandardUnit.valueOf(getDefaultUnit(key)),getDefaultUnit(key),key,Double.valueOf(value.toString()));
                }
            });
        }

    }

    private String getDefaultUnit(String metricName){
        Map<String,String> metricUnitMap = new HashMap<>();
        metricUnitMap.put("mem","Kilobytes");
        metricUnitMap.put("mem.free","Kilobytes");
        metricUnitMap.put("heap","Kilobytes");
        metricUnitMap.put("heap.used","Kilobytes");
        metricUnitMap.put("threads","Count");
        metricUnitMap.put("classes.loaded","Count");
        return metricUnitMap.getOrDefault(metricName, null);
    }

    private void publish(String metricName, StandardUnit unit,String dimensionName,String dimensionValue,double dataPoint){
        cloudWatchService.publish(cloudWatchNamespace,metricName,unit,dimensionName,dimensionValue,dataPoint);
    }

}

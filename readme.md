**Introduction**

The purpose of this library is to send your spring actuator metrics available using
/metrics endpoint generally to AWS cloudwatch with minimal effort from your side.

**How do I use this library**

You can see a sample usage on the com.kajjoy.spring.devops.cloudwatchmetrics.CloudwatchMetricsApplication file.
However here is the quickstart:

1) Define a Client configuration bean for AWS Cloudwatch, for example:

```java
@Bean
    public ClientConfiguration clientConfiguration(){
        ClientConfiguration config = new ClientConfiguration();
        config.setProxyHost(proxyHost);
        config.setProxyPort(proxyPort);
        return config;
    }

```

2.) Define an AWS Credential Provider bean:

```java
   @Bean
    public AWSCredentialsProvider credentialsProvider(){
        return new CredentialProviderChain();
    }
```

3.) Define few configurable properties for the library:

```java
spring.operational.metrics.cloudwatch.publish.cron=*/10 * * * * *
aws.cloudwatch.region=us-west-2
actuator.metrics.to.publish=mem.free,heap.used,threads.totalStarted
actuator.metrics.units=Kilobytes,Kilobytes,Count
cloudwatch.namespace=spring-actuator-cloudwatch
```

And that's about it. What do the configurable properties let you do:

a.) Set at what interval the spring actuator metrics should be published to cloudwatch.

b.) Region of your AWS account.

c.) If you want to publish only selective metrics instead of all metrics.

d.) Units that you want to use for these metrics. Allowed metrics are:
```
Valid Values: Seconds | Microseconds | Milliseconds | Bytes | Kilobytes | Megabytes | Gigabytes | Terabytes | Bits | Kilobits | Megabits | Gigabits | Terabits | Percent | Count | Bytes/Second | Kilobytes/Second | Megabytes/Second | Gigabytes/Second | Terabytes/Second | Bits/Second | Kilobits/Second | Megabits/Second | Gigabits/Second | Terabits/Second | Count/Second | None
```

e.) Custom cloudwatch namespace that you would want to use for these metrics.

**How do I get this library**

```xml
<dependency>
  <groupId>com.kajjoy.spring.devops</groupId>
  <artifactId>cloudwatch-metrics</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
```

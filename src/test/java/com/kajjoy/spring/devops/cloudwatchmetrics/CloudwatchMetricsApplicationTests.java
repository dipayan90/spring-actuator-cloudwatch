package com.kajjoy.spring.devops.cloudwatchmetrics;

import com.kajjoy.spring.devops.cloudwatchmetrics.publisher.CloudWatchPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/test.properties")
public class CloudwatchMetricsApplicationTests {

    @Resource
    private CloudWatchPublisher cloudWatchPublisher;

	@Test
	public void contextLoads() throws InterruptedException {
        cloudWatchPublisher.publishCloudWatchMetrics();
	}

}

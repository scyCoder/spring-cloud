package com.jrx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: sunchuanyin
 * @Date: 2019/4/11 12:00
 * @Version 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class EurekaProducerClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaProducerClientApplication.class, args);
    }

}

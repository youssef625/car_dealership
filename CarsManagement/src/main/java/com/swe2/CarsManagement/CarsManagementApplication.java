package com.swe2.CarsManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class CarsManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarsManagementApplication.class,args);
    }
}

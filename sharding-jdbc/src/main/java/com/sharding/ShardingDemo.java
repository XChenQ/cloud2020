package com.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShardingDemo {
    public static void main(String[] args) {
        System.out.println("----------------------");
        SpringApplication.run(ShardingDemo.class, args);
    }
}

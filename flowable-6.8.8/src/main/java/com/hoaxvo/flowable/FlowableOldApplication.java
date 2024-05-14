package com.hoaxvo.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableRetry
public class FlowableOldApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableOldApplication.class, args);
    }

}

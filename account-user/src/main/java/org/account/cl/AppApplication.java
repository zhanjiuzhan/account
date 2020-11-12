package org.account.cl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 * @author Administrator
 */
@Component
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableTransactionManagement
@EnableScheduling
@EnableEurekaClient
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class);
    }
}

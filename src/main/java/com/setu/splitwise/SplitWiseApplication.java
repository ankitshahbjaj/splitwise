package com.setu.splitwise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by anketjain on 13/04/21.
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.setu.splitwise"
})
public class SplitWiseApplication {
    @Inject
    private SplitWiseProperties splitWiseProperties;

    public static void main(String[] args) {
        SpringApplication.run(SplitWiseProperties.class, args);
    }

    @PostConstruct
    public void init() {
        log.info("Initializing application with SplitWiseProperties properties- {}", splitWiseProperties);
    }
}

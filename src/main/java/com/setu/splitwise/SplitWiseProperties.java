package com.setu.splitwise;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Created by anketjain on 13/04/21.
 */
@Data
@Component(value = "configuration")
@PropertySource("classpath:splitwise.properties")
@ConfigurationProperties
public class SplitWiseProperties {

    @NonNull
    private String environment;
}

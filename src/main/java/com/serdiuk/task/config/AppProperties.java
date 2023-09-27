package com.serdiuk.task.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.properties")
@Getter
@Setter
public class AppProperties {
    private int age;
}

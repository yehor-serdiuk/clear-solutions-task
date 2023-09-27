package com.serdiuk.task;

import com.serdiuk.task.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

}

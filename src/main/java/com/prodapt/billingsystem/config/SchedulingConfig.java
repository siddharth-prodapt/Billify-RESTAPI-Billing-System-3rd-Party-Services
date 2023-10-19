package com.prodapt.billingsystem.config;

import com.prodapt.billingsystem.config.SchedulerErrorHandler.CustomTaskSchedulerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulingConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // Set the thread pool size
        scheduler.setErrorHandler(taskSchedulerErrorHandler()); // Configure error handling
        return scheduler;
    }

    @Bean
    public CustomTaskSchedulerHandler taskSchedulerErrorHandler(){
        return new CustomTaskSchedulerHandler();
    }
}
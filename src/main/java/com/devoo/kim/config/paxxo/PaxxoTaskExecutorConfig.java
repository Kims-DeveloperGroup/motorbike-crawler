package com.devoo.kim.config.paxxo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class PaxxoTaskExecutorConfig {
    @Value("${external.paxxo.job.initThread}")
    private int initThreadCount;

    @Value("${external.paxxo.job.maxThread}")
    private int maxThreadCount;

    @Bean
    public TaskExecutor paxxoTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(initThreadCount);
        taskExecutor.setMaxPoolSize(maxThreadCount);
        return taskExecutor;
    }
}

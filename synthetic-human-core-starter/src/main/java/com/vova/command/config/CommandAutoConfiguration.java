package com.vova.command.config;

import com.vova.command.service.CommandExecutionService;
import com.vova.metric.AndroidMetrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class CommandAutoConfiguration {

    @Bean
    public ThreadPoolExecutor commandExecutor(
            @Value("${synthetic.human.core.starter.command.queue.size:1000}") int maxQueueSize,
            @Value("${synthetic.human.core.starter.command.pool.core:2}") int corePoolSize,
            @Value("${synthetic.human.core.starter.command.pool.max:4}") int maxPoolSize
    ) {
        return new ThreadPoolExecutor(
                corePoolSize, maxPoolSize, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(maxQueueSize)
        );
    }

    @Bean
    public CommandExecutionService commandExecutionService(
            @Value("${synthetic.human.core.starter.command.queue.size:1000}") int maxQueueSize,
            ThreadPoolExecutor commandExecutor,
            AndroidMetrics androidMetrics
    ) {
        return new CommandExecutionService(maxQueueSize, commandExecutor, androidMetrics);
    }
}

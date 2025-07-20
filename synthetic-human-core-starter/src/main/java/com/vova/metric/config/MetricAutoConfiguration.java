package com.vova.metric.config;

import com.vova.metric.AndroidMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class MetricAutoConfiguration {

    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Bean
    public AndroidMetrics androidMetrics(MeterRegistry meterRegistry, ThreadPoolExecutor commandExecutor) {
        return new AndroidMetrics(meterRegistry, commandExecutor);
    }
}

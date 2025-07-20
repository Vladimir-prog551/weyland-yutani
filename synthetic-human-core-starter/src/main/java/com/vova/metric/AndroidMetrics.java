package com.vova.metric;

import com.vova.command.dto.CommandRequest;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.ThreadPoolExecutor;

public class AndroidMetrics {
    private final MeterRegistry meterRegistry;
    private final ThreadPoolExecutor threadPoolExecutor;

    public AndroidMetrics(MeterRegistry meterRegistry, ThreadPoolExecutor threadPoolExecutor) {
        this.meterRegistry = meterRegistry;
        this.threadPoolExecutor = threadPoolExecutor;
        Gauge gauge = Gauge.builder("android.queue.size", threadPoolExecutor.getQueue()::size)
                .description("The current queue size")
                .register(meterRegistry);
    }

    public void recordCommandExecution(CommandRequest commandRequest) {
        Counter counter = Counter.builder("android.commands.executed")
                .tag("author", commandRequest.author())
                .description("Number of commands executed per author")
                .register(meterRegistry);
        counter.increment();
    }
}

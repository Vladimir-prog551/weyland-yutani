package com.vova.command.service;

import com.vova.audit.annotation.WeylandWatchingYou;
import com.vova.command.dto.CommandRequest;
import com.vova.command.enums.CommandPriority;
import com.vova.command.exceptions.CommandQueueOverflowException;
import com.vova.command.exceptions.CommandRequestException;
import com.vova.metric.AndroidMetrics;

import java.util.concurrent.*;

public class CommandExecutionService {

    private final ThreadPoolExecutor executor;
    private final ThreadPoolExecutor criticalThreadPool;
    private final int maxQueueSize;
    private final AndroidMetrics androidMetrics;

    public CommandExecutionService(int maxQueueSize, ThreadPoolExecutor executor, AndroidMetrics androidMetrics) {
        this.executor = executor;
        this.criticalThreadPool = new ThreadPoolExecutor(
                1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>()
        );
        this.maxQueueSize = maxQueueSize;
        this.androidMetrics = androidMetrics;
    }

    @WeylandWatchingYou
    public void executeCommand(CommandRequest commandRequest) {
        if (commandRequest == null) {
            throw new CommandRequestException("Command request cannot be null");
        }

        if (commandRequest.commandPriority() == CommandPriority.CRITICAL) {
            processCommandNow(commandRequest);
        } else {
            if (executor.getQueue().size() >= maxQueueSize) {
                throw new CommandQueueOverflowException("Command queue is full");
            }
            executor.submit(() -> processCommand(commandRequest));
        }
    }

    private void processCommandNow(CommandRequest commandRequest) {
        criticalThreadPool.execute(() -> processCommand(commandRequest));
    }

    private void processCommand(CommandRequest commandRequest) {
        System.out.println("Executing command: " + commandRequest.toString());
        androidMetrics.recordCommandExecution(commandRequest);
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }
}

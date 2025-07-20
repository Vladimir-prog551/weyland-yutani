package com.vova.command.exceptions;

public class CommandQueueOverflowException  extends RuntimeException {

    public CommandQueueOverflowException(String message) {
        super(message);
    }
}

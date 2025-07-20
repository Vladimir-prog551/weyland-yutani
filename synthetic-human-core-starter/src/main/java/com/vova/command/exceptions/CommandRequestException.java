package com.vova.command.exceptions;

public class CommandRequestException extends RuntimeException {
    public CommandRequestException(String message) {
        super(message);
    }
}

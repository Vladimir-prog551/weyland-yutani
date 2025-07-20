package com.vova.exception.dto;

import java.time.Instant;

public record ErrorInfo(String error, String message, int status, Instant timestamp) {
}

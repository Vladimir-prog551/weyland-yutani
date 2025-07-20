package com.vova.audit.dto;

public record AuditEvent(String methodName, Object[] args, Object result) {
}

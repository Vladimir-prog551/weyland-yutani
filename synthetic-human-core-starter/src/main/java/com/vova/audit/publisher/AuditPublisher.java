package com.vova.audit.publisher;

import com.vova.audit.dto.AuditEvent;

public interface AuditPublisher {
    void publish(AuditEvent auditEvent);
}

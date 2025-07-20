package com.vova.audit.publisher;

import com.vova.audit.dto.AuditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "synthetic.human.core.starter.audit.mode", havingValue = "logger", matchIfMissing = true)
@Component
public class LoggerAuditPublisher implements AuditPublisher {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void publish(AuditEvent auditEvent) {
        logger.info(auditEvent.toString());
    }
}

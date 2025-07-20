package com.vova.audit.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vova.audit.dto.AuditEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "synthetic.human.core.starter.audit.mode", havingValue = "kafka")
@Component
public class KafkaAuditPublisher implements AuditPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaAuditPublisher(
            @Qualifier("auditKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
            String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(AuditEvent auditEvent) {
        try {
            String event = objectMapper.writeValueAsString(auditEvent);
            kafkaTemplate.send(topic, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
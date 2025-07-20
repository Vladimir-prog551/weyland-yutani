package com.vova.audit.config;

import com.vova.audit.aspect.AuditAspect;
import com.vova.audit.publisher.AuditPublisher;
import com.vova.audit.publisher.KafkaAuditPublisher;
import com.vova.audit.publisher.LoggerAuditPublisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class AuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditAspect auditAspect(AuditPublisher auditPublisher) {
        return new AuditAspect(auditPublisher);
    }

    @Bean
    @ConditionalOnProperty(name = "synthetic.human.core.starter.audit.mode", havingValue = "logger", matchIfMissing = true)
    public AuditPublisher loggerAuditPublisher() {
        return new LoggerAuditPublisher();
    }

    @Bean
    @ConditionalOnProperty(name = "synthetic.human.core.starter.audit.mode", havingValue = "kafka")
    public AuditPublisher kafkaAuditPublisher(
            @Qualifier("auditKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
            @Value("${synthetic.human.core.starter.audit.kafka.topic:audit-topic}") String topic) {
        return new KafkaAuditPublisher(kafkaTemplate, topic);
    }
}
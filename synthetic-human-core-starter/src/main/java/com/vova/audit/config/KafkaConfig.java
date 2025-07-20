package com.vova.audit.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@ConditionalOnProperty(name = "synthetic.human.core.starter.audit.mode", havingValue = "kafka")
@Configuration
public class KafkaConfig {

    @Bean
    NewTopic createTopic(@Value("${synthetic.human.core.starter.audit.kafka.topic:audit-topic}") String topicName) {
        return TopicBuilder.name(topicName)
                .build();
    }

    @Bean
    public KafkaTemplate<String, String> auditKafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}

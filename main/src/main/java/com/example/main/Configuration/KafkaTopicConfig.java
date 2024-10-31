package com.example.main.Configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    // 配置 KafkaAdmin，用于管理 Kafka 主题
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    // 配置 Kafka 主题，指定分区和副本数量
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("orderTopic")
                .partitions(3)  // 设置 3 个分区
                .replicas(1)    // 设置 1 个副本
                .build();
    }
}

package com.example.main.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    @Autowired
    public KafkaTemplate<String,String> kafkaTemplate;
    public <T> void send(String topic, String key, T value) {
        try {
            // 将对象序列化为JSON字符串
            String jsonValue = JsonUtil.toJson(value);
            if(key == null)
                kafkaTemplate.send(topic, jsonValue);
            else
                kafkaTemplate.send(topic, key, jsonValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

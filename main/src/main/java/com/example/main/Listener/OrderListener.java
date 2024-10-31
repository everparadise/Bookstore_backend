package com.example.main.Listener;

import com.example.main.dto.OrderDto;
import com.example.main.model.Order;
import com.example.main.service.OrderService;
import com.example.main.util.JsonUtil;
import com.example.main.util.WebSocketServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Component
public class OrderListener {
    @Autowired
    OrderService orderService;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    WebSocketServer webSocketServer;

    @KafkaListener(topics = "orderTopic", groupId = "order")
    public void listenOrder(ConsumerRecord<String, String> record, Acknowledgment acknowledgment){
        String token = null;

        try{
            String jsonValue = record.value();
            OrderDto orderDto = JsonUtil.fromJson(jsonValue, OrderDto.class);
            System.out.println("Message TimeStamp: " + record.timestamp());
            token = orderDto.getToken();
            Order order = orderService.AddOrder(orderDto);
            System.out.println("Order Execute Over!");
            System.out.println("Order Result Send: Success");
            webSocketServer.sendResultToUser(token, "ok " + order.getOid());
            webSocketServer.closeSession(token);
            acknowledgment.acknowledge();
        }catch(Exception exception){
            if(token == null) {
                System.out.println("json parser error");
            }
            else{
                System.out.println("Order Result Send: failure");
                webSocketServer.sendResultToUser(token, "wrong " + exception.getMessage());
                webSocketServer.closeSession(token);
            }
        }
    }
}

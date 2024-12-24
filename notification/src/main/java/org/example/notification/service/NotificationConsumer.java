package org.example.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consumeOrderEvent(String message) {
        System.out.println("Consumed message: " + message);

        // Assuming the message contains orderId and status as JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long orderId = jsonNode.get("orderId").asLong();
            String status = jsonNode.get("status").asText();

            // Generate notification based on the event
            notificationService.createNotification(orderId, "Order status changed to: " + status);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

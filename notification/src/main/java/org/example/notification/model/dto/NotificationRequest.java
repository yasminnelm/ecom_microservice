package org.example.notification.model.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private Long orderId;
    private String message;

    // Getters and setters
}

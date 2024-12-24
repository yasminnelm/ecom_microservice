package org.example.notification.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private Long orderId;
    private String message;
    private String status;
    private LocalDateTime createdAt;

    // Constructor
    public NotificationResponse(Long id, Long orderId, String message, String status, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters
}

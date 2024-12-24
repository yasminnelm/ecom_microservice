package org.example.notification.service;

import org.example.notification.model.dto.NotificationResponse;
import org.example.notification.model.entity.Notification;
import org.example.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationResponse createNotification(Long orderId, String message) {
        Notification notification = new Notification();
        notification.setOrderId(orderId);
        notification.setMessage(message);
        notification.setStatus("PENDING");

        Notification savedNotification = notificationRepository.save(notification);

        // Simulate sending the notification
        try {
            // Replace with actual email/SMS logic if needed
            System.out.println("Sending notification: " + message);
            savedNotification.setStatus("SENT");
        } catch (Exception e) {
            savedNotification.setStatus("FAILED");
        }

        notificationRepository.save(savedNotification);

        return new NotificationResponse(
                savedNotification.getId(),
                savedNotification.getOrderId(),
                savedNotification.getMessage(),
                savedNotification.getStatus(),
                savedNotification.getCreatedAt()
        );
    }

    public NotificationResponse getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        return new NotificationResponse(
                notification.getId(),
                notification.getOrderId(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getCreatedAt()
        );
    }

    public List<NotificationResponse> getNotificationsByOrderId(Long orderId) {
        return notificationRepository.findByOrderId(orderId).stream()
                .map(notification -> new NotificationResponse(
                        notification.getId(),
                        notification.getOrderId(),
                        notification.getMessage(),
                        notification.getStatus(),
                        notification.getCreatedAt()))
                .collect(Collectors.toList());
    }
}

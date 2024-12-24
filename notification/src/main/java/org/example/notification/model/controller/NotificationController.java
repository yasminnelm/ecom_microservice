package org.example.notification.model.controller;

import org.example.notification.model.dto.NotificationRequest;
import org.example.notification.model.dto.NotificationResponse;
import org.example.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody NotificationRequest request) {
        NotificationResponse response = notificationService.createNotification(request.getOrderId(), request.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
        NotificationResponse response = notificationService.getNotificationById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByOrderId(@PathVariable Long orderId) {
        List<NotificationResponse> responses = notificationService.getNotificationsByOrderId(orderId);
        return ResponseEntity.ok(responses);
    }
}

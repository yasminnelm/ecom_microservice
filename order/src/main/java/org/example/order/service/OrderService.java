package org.example.order.service;

import org.example.order.feign.ProductClient;
import org.example.order.model.dto.OrderRequest;
import org.example.order.model.dto.OrderResponse;
import org.example.order.model.entity.Order;
import org.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    public OrderResponse createOrder(OrderRequest request) {
        // Check product availability
        ProductResponse product = productClient.getProductById(request.getProductId());
        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product ID: " + request.getProductId());
        }

        // Calculate total price
        Double totalPrice = product.getPrice() * request.getQuantity();

        // Create order
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(totalPrice);
        order.setStatus("PENDING");

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Update product stock
        productClient.updateProductStock(request.getProductId(), -request.getQuantity());

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                savedOrder.getStatus(),
                savedOrder.getCreatedAt()
        );
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderResponse(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getProductId(),
                        order.getQuantity(),
                        order.getTotalPrice(),
                        order.getStatus(),
                        order.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus().equals("CONFIRMED")) {
            throw new RuntimeException("Cannot cancel a confirmed order");
        }

        // Update product stock
        productClient.updateProductStock(order.getProductId(), order.getQuantity());

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}

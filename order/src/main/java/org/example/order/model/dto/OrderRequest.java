package org.example.order.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequest {
    private Long productId;
    private Integer quantity;

    // Getters and setters
}

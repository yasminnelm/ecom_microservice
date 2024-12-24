package org.example.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {
//    @GetMapping("/products/{id}")
//    ProductResponse getProductById(@PathVariable Long id);

    @PutMapping("/products/{id}")
    void updateProductStock(@PathVariable Long id, @RequestParam("quantity") Integer quantity);
}
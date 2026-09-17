package com.ecommerce.order_service.client;

import com.ecommerce.order_service.dto.ProductResponse;
import com.ecommerce.order_service.security.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "product-service",
        configuration = FeignConfig.class
)
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductResponse getProductById(
            @PathVariable Long id
    );
}
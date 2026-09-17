package com.ecommerce.order_service.client;

import com.ecommerce.order_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/api/auth/users/{email}")
    UserResponse getUserByEmail(
            @PathVariable String email
    );
}

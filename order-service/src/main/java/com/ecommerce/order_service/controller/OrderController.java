package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(
            @Valid @RequestBody OrderRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return orderService.placeOrder(
                email,
                request
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public List<OrderResponse> getMyOrders(
            Authentication authentication) {

        String email = authentication.getName();

        return orderService.getOrdersByUser(email);
    }
}
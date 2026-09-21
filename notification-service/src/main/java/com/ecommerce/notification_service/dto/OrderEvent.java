package com.ecommerce.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private Long orderId;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalAmount;
}
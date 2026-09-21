package com.ecommerce.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private Long orderId;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalAmount;
}

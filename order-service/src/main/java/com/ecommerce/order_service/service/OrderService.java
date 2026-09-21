package com.ecommerce.order_service.service;

import com.ecommerce.order_service.client.AuthClient;
import com.ecommerce.order_service.client.ProductClient;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.dto.ProductResponse;
import com.ecommerce.order_service.dto.UserResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.entity.OrderStatus;
import com.ecommerce.order_service.event.OrderEvent;
import com.ecommerce.order_service.repository.OrderRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final AuthClient authClient;
    private final KafkaProducerService kafkaProducerService;

    public OrderResponse placeOrder(String email, OrderRequest request){
        UserResponse user;
        try{
            user = authClient.getUserByEmail(email);
        }
        catch (FeignException.NotFound e){
            throw new RuntimeException("User not found");
        }
        ProductResponse product;

        try {
            product = getProduct(request.getProductId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Product not found");
        }

        if (product.getQuantity() < request.getQuantity()) {
            throw new RuntimeException(
                    "Insufficient product stock"
            );
        }

        // 4. Calculate total
        BigDecimal totalAmount =
                product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()
                                )
                        );

        // 5. Create order
        Order order = Order.builder()
                .userId(user.getId())
                .productId(product.getId())
                .quantity(request.getQuantity())
                .price(product.getPrice())
                .totalAmount(totalAmount)
                .status(OrderStatus.PLACED)
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder =
                orderRepository.save(order);

        OrderEvent event = OrderEvent.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .productId(savedOrder.getProductId())
                .quantity(savedOrder.getQuantity())
                .totalAmount(savedOrder.getTotalAmount())
                .build();

        kafkaProducerService.sendOrderEvent(event);

        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> getOrdersByUser(
            String email) {

        UserResponse user =
                authClient.getUserByEmail(email);

        return orderRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    @Retry(
            name="ProductService",
            fallbackMethod = "productServiceFallback")
    @CircuitBreaker(
            name="ProductService",
            fallbackMethod = "productServiceFallback")
    public ProductResponse getProduct(Long id){
        return productClient.getProductById(id);
    }

    public ProductResponse productServiceFallback(Long id, Throwable throwable){
        throw new RuntimeException("product service currently unavailable try after sometime!!");
    }
}


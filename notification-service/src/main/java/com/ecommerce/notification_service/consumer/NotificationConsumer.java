package com.ecommerce.notification_service.consumer;

import com.ecommerce.notification_service.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationConsumer {

    @KafkaListener(
            topics = "order-events",
            groupId = "notification-group"
    )
    public void consumeOrderEvent(OrderEvent event){
        log.info(
                "Notification: Order {} placed successfully. User: {}, Product: {}, Quantity: {}, Total: {}",
                event.getOrderId(),
                event.getUserId(),
                event.getProductId(),
                event.getQuantity(),
                event.getTotalAmount()
        );

    }
}

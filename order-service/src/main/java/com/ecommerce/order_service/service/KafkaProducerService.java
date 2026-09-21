package com.ecommerce.order_service.service;

import com.ecommerce.order_service.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private final String topic = "order-events";

    public void sendOrderEvent(OrderEvent event){
        kafkaTemplate.send(topic,event);
    }

}

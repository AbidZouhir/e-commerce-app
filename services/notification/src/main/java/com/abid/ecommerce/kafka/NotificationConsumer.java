package com.abid.ecommerce.kafka;

import com.abid.ecommerce.kafka.order.OrderConfirmation;
import com.abid.ecommerce.kafka.payment.PaymentConfirmation;
import com.abid.ecommerce.notification.Notification;
import com.abid.ecommerce.notification.NotificationRepository;
import com.abid.ecommerce.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    // private final EmailService emailService;

    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccessNotification(OrderConfirmation orderConfirmation){
        log.info(String.format("Consuming the message from order-topic Topîc::%s", orderConfirmation));
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // todo send email

    }
}

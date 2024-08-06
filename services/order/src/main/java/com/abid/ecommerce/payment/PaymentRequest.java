package com.abid.ecommerce.payment;

import com.abid.ecommerce.customer.CustomerResponse;
import com.abid.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}

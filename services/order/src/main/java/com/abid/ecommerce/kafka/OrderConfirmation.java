package com.abid.ecommerce.kafka;

import com.abid.ecommerce.customer.CustomerResponse;
import com.abid.ecommerce.order.PaymentMethod;
import com.abid.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}

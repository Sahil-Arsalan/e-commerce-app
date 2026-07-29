package com.alamara.ecommerce.payment;

import com.alamara.ecommerce.customer.CustomerResponse;
import com.alamara.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}

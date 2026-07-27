package com.alamara.ecommerce.kafka;

import com.alamara.ecommerce.customer.CustomerResponse;
import com.alamara.ecommerce.order.PaymentMethod;
import com.alamara.ecommerce.record.PurchaseResponse;

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

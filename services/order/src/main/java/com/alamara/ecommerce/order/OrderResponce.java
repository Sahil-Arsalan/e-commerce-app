package com.alamara.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponce(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
        ) {
}

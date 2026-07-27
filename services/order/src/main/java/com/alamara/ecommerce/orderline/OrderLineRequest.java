package com.alamara.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        @NotNull(message = "Product is maindatory") Integer productId,
        @Positive(message = "Quantity is maindatory") double quantity
) {
}

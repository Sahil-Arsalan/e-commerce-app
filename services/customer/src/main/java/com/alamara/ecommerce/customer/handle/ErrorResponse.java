package com.alamara.ecommerce.customer.handle;

import java.util.Map;

public record ErrorResponse(
   Map<String,String> errors
) {
}

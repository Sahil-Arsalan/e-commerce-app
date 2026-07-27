package com.alamara.ecommerce.handle;

import java.util.Map;

public record ErrorResponse(
   Map<String,String> errors
) {
}

package com.alamara.ecommerce.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}

package com.turkcell.customer_service.common.exceptions.types;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

package com.turkcell.customer_service.common.exceptions.types;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}

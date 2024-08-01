package com.turkcell.customer_service.exception.types;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}

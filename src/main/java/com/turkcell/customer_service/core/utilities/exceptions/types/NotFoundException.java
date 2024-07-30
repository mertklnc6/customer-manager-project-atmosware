package com.turkcell.customer_service.core.utilities.exceptions.types;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}

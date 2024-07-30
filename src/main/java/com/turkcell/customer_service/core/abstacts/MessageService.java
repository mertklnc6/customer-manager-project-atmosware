package com.turkcell.customer_service.core.abstacts;

public interface MessageService {
    String getMessage(String key);

    String getMessage(String key, Object[] args);
}

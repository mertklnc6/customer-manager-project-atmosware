package com.turkcell.customer_service.dto.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatedCustomerResponse(
        UUID id,
        String name,
        String surname,
        String citizenNumber,
        LocalDateTime createdDate,
        boolean isActive
) {
}

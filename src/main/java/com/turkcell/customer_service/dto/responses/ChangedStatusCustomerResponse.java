package com.turkcell.customer_service.dto.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChangedStatusCustomerResponse(
        UUID id,
        String name,
        String surname,
        String citizenNumber,
        boolean isActive,
        LocalDateTime updatedDate
) {
}

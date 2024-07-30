package com.turkcell.customer_service.business.dtos.responses;

import java.time.LocalDate;
import java.util.UUID;

public record CreatedCustomerResponse(
        UUID id,
        String name,
        String surname,
        String citizenNumber,
        LocalDate createdDate,
        boolean isActive
) {
}

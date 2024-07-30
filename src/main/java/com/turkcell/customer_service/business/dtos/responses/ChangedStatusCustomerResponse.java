package com.turkcell.customer_service.business.dtos.responses;

import java.util.UUID;

public record ChangedStatusCustomerResponse(
        UUID id,
        String name,
        String surname,
        String citizenNumber,
        boolean isActive
) {
}

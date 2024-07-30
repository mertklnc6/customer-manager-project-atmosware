package com.turkcell.customer_service.business.dtos.responses;

import java.util.UUID;

public record GetAllCustomerResponse(
        UUID id,
        String name,
        String surname,
        String citizenNumber,
        boolean isActive
) {
}

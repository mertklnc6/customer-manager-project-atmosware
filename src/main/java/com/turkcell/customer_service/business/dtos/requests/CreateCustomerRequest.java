package com.turkcell.customer_service.business.dtos.requests;

import com.turkcell.customer_service.annotations.validation.BirthDate;
import com.turkcell.customer_service.business.constants.Regexes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateCustomerRequest(

        @NotNull
        @Size(min =2 , max = 30)
        String name,

        @NotNull
        @Size(min =2 , max = 30)
        String surname,

        @NotNull
        @BirthDate
        LocalDate birthDate,

        @NotNull
        @Pattern(regexp = Regexes.TR_IDENTITY_VALIDATOR)
        String citizenNumber,

        @NotNull
        boolean isActive
) {
}

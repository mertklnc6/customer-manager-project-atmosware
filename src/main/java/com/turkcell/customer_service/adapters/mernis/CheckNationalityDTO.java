package com.turkcell.customer_service.adapters.mernis;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class CheckNationalityDTO {
    private String citizenNumber;
    private String name;
    private String surname;
    private LocalDate birthDate;
}
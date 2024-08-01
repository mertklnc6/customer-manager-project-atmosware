package com.turkcell.customer_service.service;

import com.turkcell.customer_service.constant.Messages;
import com.turkcell.customer_service.exception.types.BusinessException;
import com.turkcell.customer_service.exception.types.NotFoundException;
import com.turkcell.customer_service.external.mernis.CheckNationalityDTO;
import com.turkcell.customer_service.external.mernis.CheckNationalityService;
import com.turkcell.customer_service.model.Customer;
import com.turkcell.customer_service.repository.CustomerRepository;
import com.turkcell.customer_service.service.rules.CustomerBusinessRules;
import com.turkcell.customer_service.util.abstracts.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerBusinessRulesTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CheckNationalityService checkNationalityService;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private CustomerBusinessRules customerBusinessRules;

    @Test
    void testCitizenNumberShouldBeUnique_whenCitizenNumberAlreadyExists() {
        // Arrange
        String citizenNumber = "12345678901";
        when(customerRepository.findByCitizenNumber(citizenNumber))
                .thenReturn(Optional.of(new Customer()));

        when(messageService.getMessage(Messages.CustomerMessages.CITIZEN_NUMBER_ALREADY_EXISTS))
                .thenReturn("Citizen number already exists.");

        // Act & Assert
        assertThrows(BusinessException.class, () -> {
            customerBusinessRules.citizenNumberShouldBeUnique(citizenNumber);
        });
    }

    @Test
    void testCitizenNumberShouldBeValid_whenCitizenNumberIsInvalid() {
        // Arrange
        Customer customer = new Customer();
        customer.setCitizenNumber("12345678901");
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setBirthDate(LocalDate.of(1990, 1, 1));

        CheckNationalityDTO dto = new CheckNationalityDTO(customer.getCitizenNumber(),
                customer.getName(), customer.getSurname(), customer.getBirthDate());

        when(checkNationalityService.validate(any(CheckNationalityDTO.class))).thenReturn(false);
        when(messageService.getMessage(Messages.CustomerMessages.CITIZEN_NUMBER_NOT_VALID))
                .thenReturn(Messages.CustomerMessages.CITIZEN_NUMBER_NOT_VALID);

        // Act & Assert
        assertThrows(BusinessException.class, () -> {
            customerBusinessRules.citizenNumberShouldBeValid(customer);
        });
    }

    @Test
    void testCustomerShouldBeExist_whenCustomerDoesNotExist() {
        // Arrange
        when(messageService.getMessage(Messages.CustomerMessages.NOT_FOUND))
                .thenReturn("Customer not found.");

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            customerBusinessRules.customerShouldBeExist(Optional.empty());
        });
    }
}

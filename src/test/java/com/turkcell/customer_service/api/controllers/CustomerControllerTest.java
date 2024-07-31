package com.turkcell.customer_service.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.turkcell.customer_service.business.abstracts.CustomerService;
import com.turkcell.customer_service.business.dtos.requests.CreateCustomerRequest;
import com.turkcell.customer_service.business.dtos.responses.*;
import com.turkcell.customer_service.entities.concretes.Customer;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private UUID customerId;
    private LocalDate birthDate;
    private String name;
    private String surname;
    private String citizenNumber;
    private boolean active;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        birthDate = LocalDate.of(1999, 6, 8);
        name = "mert";
        surname = "kılınç";
        citizenNumber = "11785912534";
        active = true;
        now = LocalDateTime.now();
    }

    @Test
    void add_shouldReturnCreatedCustomerResponse() {
        // Arrange
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(
                name, surname, birthDate, citizenNumber, active);
        CreatedCustomerResponse createdCustomerResponse = new CreatedCustomerResponse(
                customerId, name, surname, citizenNumber, now, active);

        when(customerService.add(any(CreateCustomerRequest.class))).thenReturn(createdCustomerResponse);

        // Act
        CreatedCustomerResponse response = customerController.add(createCustomerRequest);

        //Assert
        assertEquals(createdCustomerResponse, response);
        verify(customerService, times(1)).add(createCustomerRequest);

    }

    @Test
    void getAll_shouldReturnGetAllCustomerResponseList(){

        //Arrange
        GetAllCustomerResponse getAllCustomerResponse = new GetAllCustomerResponse(customerId, name, surname, citizenNumber, active);
        List<GetAllCustomerResponse> getAllCustomerResponseList = Arrays.asList(getAllCustomerResponse);

        when(customerService.getAll()).thenReturn(getAllCustomerResponseList);

        //Act
        List<GetAllCustomerResponse> response = customerController.getAll();

        //Assert
        assertEquals(getAllCustomerResponseList, response);
        verify(customerService, times(1)).getAll();

    }

    @Test
    void getByCitizenNumber_shouldReturnGetCustomerByCitizenNumberResponse(){

        //Arrange
        GetCustomerByCitizenNumber getCustomerByCitizenNumber = new GetCustomerByCitizenNumber(customerId, name, surname, citizenNumber, active);

        when(customerService.getByCitizenNumber(citizenNumber)).thenReturn(getCustomerByCitizenNumber);

        //Act
        GetCustomerByCitizenNumber response = customerController.getByCitizenNumber(citizenNumber);

        //Assert
        assertEquals(getCustomerByCitizenNumber, response);
        verify(customerService, times(1)).getByCitizenNumber(citizenNumber);

    }

    @Test
    void delete_shouldReturnDeletedCustomerResponse(){

        //Arrange
        DeletedCustomerResponse deletedCustomerResponse = new DeletedCustomerResponse(customerId, name, surname, citizenNumber,null );

        when(customerService.delete(citizenNumber)).thenReturn(deletedCustomerResponse);

        //Act
        DeletedCustomerResponse response = customerController.delete(citizenNumber);

        //Assert
        assertEquals(deletedCustomerResponse, response);
        verify(customerService, times(1)).delete(citizenNumber);
    }

    @Test
    void changeActiveStatus_shouldReturnChangedStatusCustomerResponse(){

        //Arrange
        ChangedStatusCustomerResponse changedStatusCustomerResponse = new ChangedStatusCustomerResponse(customerId, name, surname, citizenNumber,active,null);

        when(customerService.changeActiveStatus(citizenNumber)).thenReturn(changedStatusCustomerResponse);

        //Act
        ChangedStatusCustomerResponse response = customerController.changeActiveStatus(citizenNumber);

        //Assert
        assertEquals(changedStatusCustomerResponse, response);
        verify(customerService, times(1)).changeActiveStatus(citizenNumber);
    }
}

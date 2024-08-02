package com.turkcell.customer_service.service;

import com.turkcell.customer_service.constant.Messages;
import com.turkcell.customer_service.dto.requests.CreateCustomerRequest;
import com.turkcell.customer_service.exception.types.BusinessException;
import com.turkcell.customer_service.exception.types.NotFoundException;
import com.turkcell.customer_service.mapper.CustomerMapper;
import com.turkcell.customer_service.service.rules.CustomerBusinessRules;
import com.turkcell.customer_service.repository.CustomerRepository;
import com.turkcell.customer_service.dto.responses.*;
import com.turkcell.customer_service.model.Customer;
import com.turkcell.customer_service.service.impl.CustomerServiceImpl;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerBusinessRules customerBusinessRules;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private CreateCustomerRequest createCustomerRequest;
    private Customer customer;
    private String citizenNumber;
    private LocalDateTime now;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        citizenNumber  = "11785912538";
        now = LocalDateTime.now();
        customerId = UUID.randomUUID();
    }
    //checkOfferListForSTBChangeTask_when_invalidContractId_then_throwBusinessException

    @Test
    void add_shouldAddCustomer() {

        // Arrange
        CreateCustomerRequest createCustomerRequest =
                new CreateCustomerRequest("mert", "kılınç", LocalDate.of(1999, 6, 8), citizenNumber, true);
        CreatedCustomerResponse createdCustomerResponse = new CreatedCustomerResponse(customerId, "mert", "kılınç", citizenNumber, now, true);

        when(customerMapper.toCustomer(createCustomerRequest)).thenReturn(customer);
        doNothing().when(customerBusinessRules).citizenNumberShouldBeUnique(any());
        doNothing().when(customerBusinessRules).citizenNumberShouldBeValid(any());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toCreatedCustomerResponse(any(Customer.class))).thenReturn(createdCustomerResponse);

        // Act
        CreatedCustomerResponse response = customerServiceImpl.add(createCustomerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(createdCustomerResponse, response);

        verify(customerBusinessRules).citizenNumberShouldBeUnique(any());
        verify(customerBusinessRules).citizenNumberShouldBeValid(any(Customer.class));
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void add_shouldThrowBusinessException_whenCitizenNumberAlreadyExists() {

        // Arrange
        CreateCustomerRequest createCustomerRequest =
                new CreateCustomerRequest("mert", "kılınç", LocalDate.of(1999, 6, 8), citizenNumber, true);

        when(customerMapper.toCustomer(createCustomerRequest)).thenReturn(customer);
        doThrow(new BusinessException(Messages.CustomerMessages.CITIZEN_NUMBER_ALREADY_EXISTS))
                .when(customerBusinessRules).citizenNumberShouldBeUnique(any());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            customerServiceImpl.add(createCustomerRequest);
        });

        assertEquals(Messages.CustomerMessages.CITIZEN_NUMBER_ALREADY_EXISTS, exception.getMessage());

        verify(customerBusinessRules).citizenNumberShouldBeUnique(any());
        verify(customerBusinessRules, never()).citizenNumberShouldBeValid(any(Customer.class));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void add_shouldThrowBusinessException_whenCitizenNumberIsInvalid() {

        // Arrange
        CreateCustomerRequest createCustomerRequest =
                new CreateCustomerRequest("mert", "kılınç", LocalDate.of(1999, 6, 8), citizenNumber, true);

        when(customerMapper.toCustomer(createCustomerRequest)).thenReturn(customer);
        doNothing().when(customerBusinessRules).citizenNumberShouldBeUnique(any());
        doThrow(new BusinessException(Messages.CustomerMessages.CITIZEN_NUMBER_NOT_VALID))
                .when(customerBusinessRules).citizenNumberShouldBeValid(any(Customer.class));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            customerServiceImpl.add(createCustomerRequest);
        });

            assertEquals(Messages.CustomerMessages.CITIZEN_NUMBER_NOT_VALID, exception.getMessage());

        verify(customerBusinessRules).citizenNumberShouldBeUnique(any());
        verify(customerBusinessRules).citizenNumberShouldBeValid(any(Customer.class));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void getAll_shouldReturnAllCustomers() {

        // Arrange
        customer.setId(customerId);
        customer.setName("mert");
        customer.setSurname("kılınç");
        customer.setCitizenNumber(citizenNumber);
        customer.setBirthDate(LocalDate.of(1999, 6, 8));
        customer.setActive(true);
        customer.setCreatedDate(now);

        List<Customer> customerList = Arrays.asList(customer);

        List<GetAllCustomerResponse> customerResponseList = Arrays.asList(new GetAllCustomerResponse(UUID.randomUUID(), "mert", "kılınç", citizenNumber, true));

        when(customerRepository.findAll()).thenReturn(customerList);
        when(customerMapper.toGetAllCustomerResponseList(customerList)).thenReturn(customerResponseList);

        //Act
        List<GetAllCustomerResponse> response = customerServiceImpl.getAll();

        //Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(customerResponseList, response);

        verify(customerRepository).findAll();
        verify(customerMapper).toGetAllCustomerResponseList(customerList);

    }

    @Test
    void getByCitizenNumber_shouldReturnCustomer() {
        // Arrange
        customer.setId(customerId);
        customer.setName("mert");
        customer.setSurname("kılınç");
        customer.setCitizenNumber(citizenNumber);
        customer.setBirthDate(LocalDate.of(1999, 6, 8));
        customer.setActive(true);
        customer.setCreatedDate(now);

        GetCustomerByCitizenNumber customerResponse = new GetCustomerByCitizenNumber(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getCitizenNumber(),
                customer.isActive()
        );

        when(customerRepository.findByCitizenNumber(citizenNumber)).thenReturn(Optional.of(customer));
        doNothing().when(customerBusinessRules).customerShouldBeExist(any());
        when(customerMapper.toGetCustomerByCitizenNumber(any(Customer.class))).thenReturn(customerResponse);

        // Act
        GetCustomerByCitizenNumber response = customerServiceImpl.getByCitizenNumber(citizenNumber);

        // Assert
        assertNotNull(response);
        assertEquals(customerResponse, response);
        assertEquals(citizenNumber, response.citizenNumber());

        verify(customerRepository).findByCitizenNumber(citizenNumber);
        verify(customerBusinessRules).customerShouldBeExist(any(Optional.class));
        verify(customerMapper).toGetCustomerByCitizenNumber(customer);
    }

    @Test
    void getByCitizenNumber_shouldThrowNotFoundException_whenCustomerDoesNotExist() {
        // Arrange
        String nonExistentCitizenNumber = "99999999999";

        when(customerRepository.findByCitizenNumber(nonExistentCitizenNumber)).thenReturn(Optional.empty());
        doThrow(new NotFoundException(Messages.CustomerMessages.NOT_FOUND))
                .when(customerBusinessRules).customerShouldBeExist(any());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            customerServiceImpl.getByCitizenNumber(nonExistentCitizenNumber);
        });

        assertEquals(Messages.CustomerMessages.NOT_FOUND, exception.getMessage());

        verify(customerRepository).findByCitizenNumber(nonExistentCitizenNumber);
        verify(customerBusinessRules).customerShouldBeExist(any());
        verify(customerMapper, never()).toGetCustomerByCitizenNumber(any());
    }

    @Test
    void delete_shouldReturnDeletedCustomerResponse() {
        // Arrange
        customer.setId(customerId);
        customer.setName("mert");
        customer.setSurname("kılınç");
        customer.setCitizenNumber(citizenNumber);
        customer.setBirthDate(LocalDate.of(1999, 6, 8));
        customer.setActive(true);
        customer.setCreatedDate(now);
        customer.setDeletedDate(null);


        DeletedCustomerResponse deletedCustomerResponse = new DeletedCustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getCitizenNumber(),
                now
        );

        when(customerRepository.findByCitizenNumber(citizenNumber)).thenReturn(Optional.of(customer));
        doNothing().when(customerBusinessRules).customerShouldBeExist(any());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDeletedCustomerResponse(any(Customer.class))).thenReturn(deletedCustomerResponse);

        // Act
        DeletedCustomerResponse response = customerServiceImpl.delete(citizenNumber);

        // Assert
        assertNotNull(response);
        assertEquals(deletedCustomerResponse, response);
        assertEquals(customer.getId(), response.id());
        assertEquals(customer.getName(), response.name());
        assertEquals(customer.getSurname(), response.surname());
        assertEquals(customer.getCitizenNumber(), response.citizenNumber());
        assertNotNull(response.deletedDate());

        verify(customerRepository).findByCitizenNumber(citizenNumber);
        verify(customerBusinessRules).customerShouldBeExist(any(Optional.class));
        verify(customerRepository).save(any(Customer.class));
        verify(customerMapper).toDeletedCustomerResponse(any(Customer.class));
    }

    @Test
    void delete_shouldThrowNotFoundException_whenCustomerDoesNotExist() {
        // Arrange
        String nonExistentCitizenNumber = "99999999999";

        when(customerRepository.findByCitizenNumber(nonExistentCitizenNumber)).thenReturn(Optional.empty());
        doThrow(new NotFoundException(Messages.CustomerMessages.NOT_FOUND))
                .when(customerBusinessRules).customerShouldBeExist(any());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            customerServiceImpl.delete(nonExistentCitizenNumber);
        });

        assertEquals(Messages.CustomerMessages.NOT_FOUND, exception.getMessage());

        verify(customerRepository).findByCitizenNumber(nonExistentCitizenNumber);
        verify(customerBusinessRules).customerShouldBeExist(any());
        verify(customerRepository, never()).save(any());
        verify(customerMapper, never()).toDeletedCustomerResponse(any());
    }

    @Test
    void changeActiveStatus_shouldChangeCustomerActiveStatus(){
        //Arrange

        customer.setId(customerId);
        customer.setName("mert");
        customer.setSurname("kılınç");
        customer.setCitizenNumber(citizenNumber);
        customer.setBirthDate(LocalDate.of(1999, 6, 8));
        customer.setActive(true);
        customer.setCreatedDate(now);
        customer.setUpdatedDate(null);


        ChangedStatusCustomerResponse changedStatusCustomerResponse = new ChangedStatusCustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getCitizenNumber(),
                customer.isActive(),
                now
        );

        when(customerRepository.findByCitizenNumber(citizenNumber)).thenReturn(Optional.of(customer));
        doNothing().when(customerBusinessRules).customerShouldBeExist(any());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toChangedStatusCustomerResponse(any(Customer.class))).thenReturn(changedStatusCustomerResponse);

        // Act
        ChangedStatusCustomerResponse response = customerServiceImpl.changeActiveStatus(citizenNumber);

        // Assert
        assertNotNull(response);
        assertEquals(changedStatusCustomerResponse, response);
        assertEquals(customer.getId(), response.id());
        assertEquals(customer.getName(), response.name());
        assertEquals(customer.getSurname(), response.surname());
        assertEquals(customer.getCitizenNumber(), response.citizenNumber());
        assertNotNull(response.updatedDate());

        verify(customerRepository).findByCitizenNumber(citizenNumber);
        verify(customerBusinessRules).customerShouldBeExist(any(Optional.class));
        verify(customerRepository).save(any(Customer.class));
        verify(customerMapper).toChangedStatusCustomerResponse(any(Customer.class));

    }

    @Test
    void changeActiveStatus_shouldThrowNotFoundException_whenCustomerDoesNotExist() {
        // Arrange
        String nonExistentCitizenNumber = "99999999999";

        when(customerRepository.findByCitizenNumber(nonExistentCitizenNumber)).thenReturn(Optional.empty());
        doThrow(new NotFoundException(Messages.CustomerMessages.NOT_FOUND))
                .when(customerBusinessRules).customerShouldBeExist(any());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            customerServiceImpl.changeActiveStatus(nonExistentCitizenNumber);
        });

        assertEquals(Messages.CustomerMessages.NOT_FOUND, exception.getMessage());

        verify(customerRepository).findByCitizenNumber(nonExistentCitizenNumber);
        verify(customerBusinessRules).customerShouldBeExist(any());
        verify(customerRepository, never()).save(any());
        verify(customerMapper, never()).toChangedStatusCustomerResponse(any());
    }




}



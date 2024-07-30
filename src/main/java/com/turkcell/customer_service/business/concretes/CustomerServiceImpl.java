package com.turkcell.customer_service.business.concretes;

import com.turkcell.customer_service.business.abstracts.CustomerService;
import com.turkcell.customer_service.business.dtos.requests.CreateCustomerRequest;
import com.turkcell.customer_service.business.dtos.responses.*;
import com.turkcell.customer_service.business.mappers.CustomerMapper;
import com.turkcell.customer_service.business.rules.CustomerBusinessRules;
import com.turkcell.customer_service.data_access.abstracts.CustomerRepository;
import com.turkcell.customer_service.entities.concretes.Customer;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerBusinessRules customerBusinessRules;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CreatedCustomerResponse add(CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.customerMapper.toCustomer(createCustomerRequest);

        this.customerBusinessRules.citizenNumberShouldBeValid(customer);
        this.customerBusinessRules.citizenNumberShouldBeUnique(customer.getCitizenNumber());

        this.customerRepository.save(customer);

        return this.customerMapper.toCreatedCustomerResponse(customer);
    }

    @Override
    public List<GetAllCustomerResponse> getAll() {
        List<Customer> customers =  this.customerRepository.findAll();

        return this.customerMapper.toGetAllCustomerResponse(customers);
    }

    @Override
    public GetCustomerByCitizenNumber getByCitizenNumber(String citizenNumber) {
        Optional<Customer> customerOptional = this.customerRepository.findByCitizenNumber(citizenNumber);

        this.customerBusinessRules.customerShouldBeExist(customerOptional);

        return this.customerMapper.toGetCustomerByCitizenNumber(customerOptional.get());
    }

    @Override
    public DeletedCustomerResponse delete(String citizenNumber) {
        Optional<Customer> customerOptional = this.customerRepository.findByCitizenNumber(citizenNumber);

        this.customerBusinessRules.customerShouldBeExist(customerOptional);

        customerOptional.get().setDeletedDate(LocalDateTime.now());
        return this.customerMapper.toDeletedCustomerResponse(customerOptional.get());
    }

    @Override
    public ChangedStatusCustomerResponse changeActiveStatus(String citizenNumber) {
        Optional<Customer> customerOptional = this.customerRepository.findByCitizenNumber(citizenNumber);

        this.customerBusinessRules.customerShouldBeExist(customerOptional);

        customerOptional.get().setActive(!customerOptional.get().isActive());
        return this.customerMapper.toChangedStatusCustomerResponse(customerOptional.get());
    }
}

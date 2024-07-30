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

        this.customerBusinessRules.citizenNumberShouldBeUnique(customer.getCitizenNumber());
        this.customerBusinessRules.citizenNumberShouldBeValid(customer);

        Customer savedCustomer = this.customerRepository.save(customer);

        return this.customerMapper.toCreatedCustomerResponse(savedCustomer);
    }

    @Override
    public List<GetAllCustomerResponse> getAll() {
        List<Customer> customers =  this.customerRepository.findAll();

        return this.customerMapper.toGetAllCustomerResponseList(customers);
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
        Customer customer = this.customerRepository.save(customerOptional.get());

        return this.customerMapper.toDeletedCustomerResponse(customer);
    }

    @Override
    public ChangedStatusCustomerResponse changeActiveStatus(String citizenNumber) {
        Optional<Customer> customerOptional = this.customerRepository.findByCitizenNumber(citizenNumber);

        this.customerBusinessRules.customerShouldBeExist(customerOptional);

        customerOptional.get().setActive(!customerOptional.get().isActive());
        Customer updatedCustomer = this.customerRepository.save(customerOptional.get());

        return this.customerMapper.toChangedStatusCustomerResponse(updatedCustomer);
    }
}

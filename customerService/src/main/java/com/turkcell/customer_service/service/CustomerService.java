package com.turkcell.customer_service.service;

import com.turkcell.customer_service.dto.requests.CreateCustomerRequest;
import com.turkcell.customer_service.dto.responses.*;

import java.util.List;

public interface CustomerService {

    CreatedCustomerResponse add(CreateCustomerRequest createCustomerRequest);
    List<GetAllCustomerResponse> getAll();
    GetCustomerByCitizenNumber getByCitizenNumber(String citizenNumber);
    DeletedCustomerResponse delete(String citizenNumber);
    ChangedStatusCustomerResponse changeActiveStatus(String citizenNumber);

}

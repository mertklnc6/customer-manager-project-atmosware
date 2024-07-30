package com.turkcell.customer_service.business.abstracts;

import com.turkcell.customer_service.business.dtos.requests.CreateCustomerRequest;
import com.turkcell.customer_service.business.dtos.responses.*;

import java.util.List;

public interface CustomerService {

    CreatedCustomerResponse add(CreateCustomerRequest createCustomerRequest);
    List<GetAllCustomerResponse> getAll();
    GetCustomerByCitizenNumber getByCitizenNumber(String citizenNumber);
    DeletedCustomerResponse delete(String citizenNumber);
    ChangedStatusCustomerResponse changeActiveStatus(String citizenNumber);

}

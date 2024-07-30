package com.turkcell.customer_service.business.mappers;

import com.turkcell.customer_service.business.dtos.requests.CreateCustomerRequest;
import com.turkcell.customer_service.business.dtos.responses.*;
import com.turkcell.customer_service.common.mapping.MapstructService;
import com.turkcell.customer_service.entities.concretes.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructService.class)
public interface CustomerMapper{

    Customer toCustomer(CreateCustomerRequest createCustomerRequest);
    CreatedCustomerResponse toCreatedCustomerResponse(Customer customer);
    List<GetAllCustomerResponse> toGetAllCustomerResponse(List<Customer> customers);
    GetAllCustomerResponse toGetAllCustomerResponse(Customer customer);
    GetCustomerByCitizenNumber toGetCustomerByCitizenNumber(Customer customer);
    DeletedCustomerResponse toDeletedCustomerResponse(Customer customer);
    ChangedStatusCustomerResponse toChangedStatusCustomerResponse(Customer customer);
}

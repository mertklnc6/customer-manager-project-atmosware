package com.turkcell.customer_service.business.mappers;

import com.turkcell.customer_service.business.dtos.requests.CreateCustomerRequest;
import com.turkcell.customer_service.business.dtos.responses.*;
import com.turkcell.customer_service.common.mapping.MapstructService;
import com.turkcell.customer_service.entities.concretes.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructService.class)
public interface CustomerMapper{

    @Mapping(target = "active", source = "isActive")
    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    @Mapping(target = "isActive", source = "active")
    CreatedCustomerResponse toCreatedCustomerResponse(Customer customer);

    @Mapping(target = "isActive", source = "active")
    List<GetAllCustomerResponse> toGetAllCustomerResponseList(List<Customer> customers);

    @Mapping(target = "isActive", source = "active")
    GetAllCustomerResponse toGetAllCustomerResponse(Customer customer);

    @Mapping(target = "isActive", source = "active")
    GetCustomerByCitizenNumber toGetCustomerByCitizenNumber(Customer customer);

    DeletedCustomerResponse toDeletedCustomerResponse(Customer customer);

    @Mapping(target = "isActive", source = "active")
    ChangedStatusCustomerResponse toChangedStatusCustomerResponse(Customer customer);
}

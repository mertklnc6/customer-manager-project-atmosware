package com.turkcell.customer_service.api.controllers;

import com.turkcell.customer_service.business.abstracts.CustomerService;
import com.turkcell.customer_service.business.dtos.requests.CreateCustomerRequest;
import com.turkcell.customer_service.business.dtos.responses.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedCustomerResponse add(@Valid @RequestBody CreateCustomerRequest createCustomerRequest){
        return this.customerService.add(createCustomerRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<GetAllCustomerResponse> getAll(){
        return this.customerService.getAll();
    }

    @GetMapping({"/by-citizen-number"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GetCustomerByCitizenNumber getByCitizenNumber(@Valid String citizenNumber){
        return this.customerService.getByCitizenNumber(citizenNumber);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeletedCustomerResponse delete(@Valid String citizenNumber){
        return this.customerService.delete(citizenNumber);
    }

    @PutMapping({"/change-active-status"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChangedStatusCustomerResponse changeActiveStatus(@Valid String citizenNumber){
        return this.customerService.changeActiveStatus(citizenNumber);
    }
}

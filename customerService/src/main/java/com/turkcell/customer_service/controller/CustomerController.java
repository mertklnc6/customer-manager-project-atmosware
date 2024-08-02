package com.turkcell.customer_service.controller;

import com.turkcell.customer_service.dto.requests.CreateCustomerRequest;
import com.turkcell.customer_service.dto.responses.*;
import com.turkcell.customer_service.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Management System", description = "Operations related to customer management")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new customer",
            description = "Creates a new customer and returns the created customer details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = CreateCustomerRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Customer created successfully",
                            content = @Content(schema = @Schema(implementation = CreatedCustomerResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    public CreatedCustomerResponse add(
            @Valid @RequestBody @Parameter(description = "Customer data to be created") CreateCustomerRequest createCustomerRequest) {
        return this.customerService.add(createCustomerRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all customers",
            description = "Returns a list of all customers.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of customers",
                            content = @Content(schema = @Schema(implementation = GetAllCustomerResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public List<GetAllCustomerResponse> getAll() {
        return this.customerService.getAll();
    }

    @GetMapping("/by-citizen-number/{citizenNumber}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get customer by citizen number",
            description = "Returns customer details based on the provided citizen number.",
            parameters = @Parameter(name = "citizenNumber", description = "Citizen number of the customer to be retrieved"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved customer",
                            content = @Content(schema = @Schema(implementation = GetCustomerByCitizenNumber.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid citizen number format")
            })
    public GetCustomerByCitizenNumber getByCitizenNumber(
            @Valid @PathVariable @Parameter(description = "The citizen number of the customer") String citizenNumber) {
        return this.customerService.getByCitizenNumber(citizenNumber);
    }

    @DeleteMapping("/delete/{citizenNumber}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete customer by citizen number",
            description = "Deletes a customer based on the provided citizen number and returns the result.",
            parameters = @Parameter(name = "citizenNumber", description = "Citizen number of the customer to be deleted"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted customer",
                            content = @Content(schema = @Schema(implementation = DeletedCustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid citizen number format")
            })
    public DeletedCustomerResponse delete(
            @Valid @PathVariable @Parameter(description = "The citizen number of the customer to be deleted") String citizenNumber) {
        return this.customerService.delete(citizenNumber);
    }

    @PatchMapping("/change-active-status/{citizenNumber}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change customer active status",
            description = "Changes the active status of a customer based on the provided citizen number and returns the updated status.",
            parameters = @Parameter(name = "citizenNumber", description = "Citizen number of the customer whose status will be changed"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully changed customer status",
                            content = @Content(schema = @Schema(implementation = ChangedStatusCustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid citizen number format")
            })
    public ChangedStatusCustomerResponse changeActiveStatus(
            @Valid @PathVariable @Parameter(description = "The citizen number of the customer whose status will be changed") String citizenNumber) {
        return this.customerService.changeActiveStatus(citizenNumber);
    }
}

package com.turkcell.customer_service.service.rules;

import com.turkcell.customer_service.constant.Messages;
import com.turkcell.customer_service.exception.types.BusinessException;
import com.turkcell.customer_service.exception.types.NotFoundException;
import com.turkcell.customer_service.external.mernis.CheckNationalityDTO;
import com.turkcell.customer_service.external.mernis.CheckNationalityService;
import com.turkcell.customer_service.model.Customer;
import com.turkcell.customer_service.repository.CustomerRepository;
import com.turkcell.customer_service.util.abstracts.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerBusinessRules {
    private final CustomerRepository customerRepository;
    private final CheckNationalityService checkNationalityService;
    private final MessageService messageService;


    public void citizenNumberShouldBeUnique(String citizenNumber) {
        Optional<Customer> customer = customerRepository.findByCitizenNumber(citizenNumber);
        if (customer.isPresent()) {
            throw new BusinessException(messageService.getMessage(Messages.CustomerMessages.CITIZEN_NUMBER_ALREADY_EXISTS));
        }
    }

    public void citizenNumberShouldBeValid(Customer customer) {
        boolean result = checkNationalityService.validate(new CheckNationalityDTO(customer.getCitizenNumber(),
                customer.getName() , customer.getSurname(), customer.getBirthDate()));
        if (!result) {
            throw new BusinessException(messageService.getMessage(Messages.CustomerMessages.CITIZEN_NUMBER_NOT_VALID));
        }
    }

    public void customerShouldBeExist(Optional<Customer> customer) {
        if (customer.isEmpty()) {
            throw new NotFoundException(messageService.getMessage(Messages.CustomerMessages.NOT_FOUND));
        }
    }
}

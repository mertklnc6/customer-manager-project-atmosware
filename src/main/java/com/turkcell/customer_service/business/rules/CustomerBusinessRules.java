package com.turkcell.customer_service.business.rules;

import com.turkcell.customer_service.adapters.mernis.CheckNationalityDTO;
import com.turkcell.customer_service.adapters.mernis.CheckNationalityService;
import com.turkcell.customer_service.business.constants.Messages;
import com.turkcell.customer_service.core.abstacts.MessageService;
import com.turkcell.customer_service.core.utilities.exceptions.types.BusinessException;
import com.turkcell.customer_service.core.utilities.exceptions.types.NotFoundException;
import com.turkcell.customer_service.data_access.abstracts.CustomerRepository;
import com.turkcell.customer_service.entities.concretes.Customer;
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

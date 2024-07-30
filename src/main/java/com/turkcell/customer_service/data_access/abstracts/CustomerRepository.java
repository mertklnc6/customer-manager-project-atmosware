package com.turkcell.customer_service.data_access.abstracts;

import com.turkcell.customer_service.entities.concretes.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByCitizenNumber(String citizenNumber);
}

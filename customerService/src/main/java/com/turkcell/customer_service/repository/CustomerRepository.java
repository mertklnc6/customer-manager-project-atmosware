package com.turkcell.customer_service.repository;

import com.turkcell.customer_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByCitizenNumber(String citizenNumber);
}

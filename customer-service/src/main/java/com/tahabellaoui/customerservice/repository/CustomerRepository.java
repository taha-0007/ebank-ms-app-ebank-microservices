package com.tahabellaoui.customerservice.repository;

import com.tahabellaoui.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
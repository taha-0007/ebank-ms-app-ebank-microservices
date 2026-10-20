package com.tahabellaoui.customerservice.service;

import com.tahabellaoui.customerservice.entities.Customer;
import com.tahabellaoui.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Not found"));
    }


    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
package com.tahabellaoui.ebankservice.feign;

import com.tahabellaoui.ebankservice.model.Customer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker; // L'import crucial
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerRestClient {

    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "customerService", fallbackMethod = "getDefaultCustomer") // L'annotation du prof
    Customer getCustomerById(@PathVariable Long id);

    // La méthode de secours (Fallback) si le customer-service est en panne
    default Customer getDefaultCustomer( Long id , Exception e) {
        return new Customer(id, "Not available", "Not available");
    }
}
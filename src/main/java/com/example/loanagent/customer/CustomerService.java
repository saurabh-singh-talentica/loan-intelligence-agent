package com.example.loanagent.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CustomerService {

    private final Map<Long, Customer> customers = Map.of(

            5001L,
            new Customer(
                    5001L,
                    "Rahul",
                    "rahul@example.com"
            ),

            5002L,
            new Customer(
                    5002L,
                    "Amit",
                    "amit@example.com"
            )
    );

    public Customer getCustomer(Long customerId) {
        log.info("Retrieving customer details for customerId: {}", customerId);
        Customer customer = customers.get(customerId);

        if (customer == null) {
            log.error("Customer not found for customerId: {}", customerId);
            throw new IllegalArgumentException(
                    "Customer not found: " + customerId
            );
        }
        log.info("Customer found for customerId: {}", customerId);
        return customer;
    }
}

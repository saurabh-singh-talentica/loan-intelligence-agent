package com.example.loanagent.customer;

public record Customer(
        Long customerId,
        String name,
        String email
) {
}
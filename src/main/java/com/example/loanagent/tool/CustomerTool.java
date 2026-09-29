package com.example.loanagent.tool;

import com.example.loanagent.customer.Customer;
import com.example.loanagent.customer.CustomerService;
import com.example.loanagent.tool.dto.CustomerToolResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerTool {

    private final CustomerService customerService;

    public CustomerTool(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Tool(
            name = "getCustomerDetails",
            description = """
                Retrieve customer details using customer ID.
                """
    )
    public CustomerToolResponse getCustomerDetails(@ToolParam(description = "Unique customer ID") Long customerId) {
        log.info("Customer Tool -> Fetching customer details for customerId: {}", customerId);
        try {
            Customer customer = customerService.getCustomer(customerId);
            return new CustomerToolResponse(
                    true,
                    customer.customerId(),
                    customer.name(),
                    customer.email(),
                    null,
                    null
            );

        } catch (IllegalArgumentException exception) {
            log.error("Customer Tool -> Customer not found for customerId: {}", customerId);
            return new CustomerToolResponse(
                    false,
                    customerId,
                    null,
                    null,
                    "CUSTOMER_NOT_FOUND",
                    exception.getMessage()
            );
        }
    }
}
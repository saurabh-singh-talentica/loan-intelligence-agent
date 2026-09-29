package com.example.loanagent.dto;

import jakarta.validation.constraints.NotBlank;

public record LoanChatRequest(
        @NotBlank(message = "Message must not be blank")
        String message
) { }
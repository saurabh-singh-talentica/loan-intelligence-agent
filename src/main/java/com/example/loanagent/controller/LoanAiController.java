package com.example.loanagent.controller;

import com.example.loanagent.dto.LoanChatRequest;
import com.example.loanagent.dto.LoanChatResponse;
import com.example.loanagent.service.LoanAiService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loan-ai")
@Slf4j
public class LoanAiController {

    private final LoanAiService loanAiService;

    public LoanAiController(LoanAiService loanAiService) {
        this.loanAiService = loanAiService;
    }

    @PostMapping("/chat")
    public ResponseEntity<LoanChatResponse> chat(@Valid @RequestBody LoanChatRequest request) {
        log.info("Received chat request: {}", request.message());
        String response = loanAiService.askLoanQuestion(request.message());
        log.info("Sending chat response: {}", response);
        return ResponseEntity.ok(new LoanChatResponse(response));
    }
}
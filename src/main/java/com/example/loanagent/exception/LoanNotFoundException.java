package com.example.loanagent.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(Long loanId) {
        super("Loan not found for loan ID: " + loanId);
    }
}
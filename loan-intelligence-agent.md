# Loan Intelligence Agent

## 1. Project Overview

**Loan Intelligence Agent** is an AI-powered Spring Boot application that uses an LLM to understand loan-related queries and dynamically interact with backend services through AI tools.

The agent can retrieve loan, customer, and income information and use the retrieved data to generate a meaningful response.

---

## 2. Architecture

```text
                         ┌──────────────────────┐
                         │        User          │
                         │   Postman / Client   │
                         └──────────┬───────────┘
                                    │
                                    │ POST /api/v1/loan-ai/chat
                                    ▼
                         ┌──────────────────────┐
                         │   LoanAiController   │
                         │    Spring REST API   │
                         └──────────┬───────────┘
                                    │
                                    │ Prompt
                                    ▼
                         ┌──────────────────────┐
                         │       LLM            │
                         │     (Spring AI)      │
                         └──────────┬───────────┘
                                    │
                       Decides which tool to call
                                    │
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
              ▼                     ▼                     ▼
     ┌────────────────┐    ┌────────────────┐    ┌────────────────┐
     │   LoanTool     │    │ CustomerTool   │    │  IncomeTool    │
     └───────┬────────┘    └───────┬────────┘    └───────┬────────┘
             │                     │                     │
             ▼                     ▼                     ▼
     ┌────────────────┐    ┌────────────────┐    ┌────────────────┐
     │  LoanService   │    │CustomerService │    │ IncomeService  │
     └────────────────┘    └────────────────┘    └────────────────┘
             │                     │                     │
             └─────────────────────┼─────────────────────┘
                                   │
                                   ▼
                         Tool Response DTO
                                   │
                                   ▼
                         ┌──────────────────────┐
                         │        LLM           │
                         │  Generates Response  │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │      User            │
                         │  Natural Language    │
                         │      Response        │
                         └──────────────────────┘
```
----


## 3. Request Flow

Example user question:

> **Why is loan 1001 pending?**

### Step 1 — User Request

The client sends:

```http
POST /api/v1/loan-ai/chat
```

```json
{
  "message": "Why is loan 1001 pending?"
}
```

### Step 2 — LLM Understands the Query

The LLM identifies that it needs information about loan `1001`.

It decides to call:

```text
getLoanDetails(1001)
```

### Step 3 — Loan Tool Executes

`LoanTool` calls:

```text
LoanService → getLoan(1001)
```

Example response:

```json
{
  "success": true,
  "loanId": 1001,
  "customerId": 5001,
  "status": "DISB_REVIEW",
  "pendingProcess": "INCOME_VERIFICATION"
}
```

### Step 4 — Agent Decides Next Action

The LLM sees that the loan is pending because of:

```text
INCOME_VERIFICATION
```

It decides that additional information is required and calls:

```text
getIncomeDetails(1001)
```

### Step 5 — Income Tool Executes

`IncomeTool` calls:

```text
IncomeService → getIncomeDetails(1001)
```

Example response:

```json
{
  "success": true,
  "loanId": 1001,
  "monthlyIncome": 75000,
  "verificationStatus": "FAILED",
  "failureReason": "BANK_STATEMENT_MISMATCH"
}
```

### Step 6 — LLM Generates Final Response

The LLM combines both tool responses and generates:

> Loan 1001 is currently pending because income verification has failed. The reason for the failure is a bank statement mismatch.

---

## 4. Available AI Tools

| Tool                 | Purpose                                                  |
| -------------------- | -------------------------------------------------------- |
| `getLoanDetails`     | Retrieves loan status, customer ID and pending process   |
| `getCustomerDetails` | Retrieves customer information                           |
| `getIncomeDetails`   | Retrieves income verification details and failure reason |

The LLM decides **which tool to call and when to call it**.

---

## 5. Multi-Step Agent Flow

The important part of the project is that the workflow is **not hardcoded** in the service.

For example:

```text
User Question
     ↓
LLM
     ↓
getLoanDetails()
     ↓
Tool Result
     ↓
LLM analyzes result
     ↓
Does it need more information?
     ↓
Yes
     ↓
getIncomeDetails()
     ↓
Tool Result
     ↓
LLM
     ↓
Final Answer
```

This demonstrates the basic concept of an **AI Agent**:

> **LLM decides → Tool executes → Result returns → LLM decides next action**

---

## 6. Tool Response Design

Instead of directly exposing internal Java objects to the LLM, the application uses dedicated tool response DTOs.

Example:

```json
{
  "success": false,
  "loanId": 9999,
  "errorCode": "LOAN_NOT_FOUND",
  "message": "Loan not found for loan ID: 9999"
}
```

This provides a controlled contract between the backend and the AI.

Benefits:

* Prevents unnecessary internal data exposure
* Gives the LLM structured information
* Makes business errors understandable
* Reduces chances of hallucinating missing data
* Keeps AI-facing contracts independent from domain models

---

## 7. Technology Stack

* **Java 21**
* **Spring Boot 3.5.x**
* **Spring AI**
* **OpenAI LLM**
* **Spring Web**
* **Maven**
* **Postman** for API testing

---


### Core Concept

**Traditional Backend**

```text
Request → Controller → Service → Response
```

**AI Agent Backend**

```text
User
  ↓
LLM
  ↓
Decide Action
  ↓
Backend Tool
  ↓
Tool Result
  ↓
LLM
  ↓
Final Response
```

```
```

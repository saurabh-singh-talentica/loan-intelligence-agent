package com.example.loanagent.service;

import com.example.loanagent.tool.CustomerTool;
import com.example.loanagent.tool.IncomeTool;
import com.example.loanagent.tool.LoanTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LoanAiService {

    private final ChatClient chatClient;

    private final LoanTool loanTool;
    private final CustomerTool customerTool;
    private final IncomeTool incomeTool;

    public LoanAiService(
            ChatClient.Builder chatClientBuilder,
            LoanTool loanTool,
            CustomerTool customerTool,
            IncomeTool incomeTool) {

        this.chatClient = chatClientBuilder.build();

        this.loanTool = loanTool;
        this.customerTool = customerTool;
        this.incomeTool = incomeTool;
    }

    public String askLoanQuestion(String message) {

        return chatClient
                .prompt() // start a new chat prompt
                .system("""
                        You are a Loan Intelligence Assistant.

                        Your job is to help users understand
                        loan processing and loan status.

                        IMPORTANT RULES:

                        1. Never invent actual loan information.

                        2. When the user asks about a specific loan,
                           use the available tools to retrieve actual data.

                        3. If the result of one tool indicates that
                           additional information is required, use
                           another appropriate tool.

                        4. Continue using tools until you have enough
                           information to answer the user's question.

                        5. Give a clear explanation based on the
                           information returned by the tools.
                        """) // set the system message to define the AI's role and rules
                .user(message) // user message is the question from the user
                .tools(
                        loanTool,
                        customerTool,
                        incomeTool
                )  // register the tools with the chat client, llm will decide which tool to use based on the user message and the system message
                .call()// call the chat client to process the prompt and tools
                .content(); // return the content of the response from the chat client
    }
}
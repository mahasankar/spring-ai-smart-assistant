package com.enterprise.ai.assistant.model;

import jakarta.validation.constraints.NotBlank;

public record SupportTicketRequest(
    @NotBlank(message = "Ticket ID cannot be blank")
    String ticketId,
    
    @NotBlank(message = "Customer ID cannot be blank")
    String customerId,
    
    @NotBlank(message = "Message content cannot be blank")
    String message
) {}
package com.enterprise.ai.assistant.model;

public record SupportTicketResolution(
    String ticketId,
    ActionTaken actionTaken,
    boolean refundIssued,
    double refundAmount,
    String customerResponse,
    Priority priority
) {}
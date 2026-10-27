package com.enterprise.ai.assistant.model;

public record RefundResult(
    String transactionId,
    String orderId,
    boolean success,
    double refundAmount,
    String message
) {}
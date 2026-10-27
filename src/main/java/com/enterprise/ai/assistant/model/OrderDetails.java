package com.enterprise.ai.assistant.model;

public record OrderDetails(
    String orderId,
    String status, // e.g., "IN_WAREHOUSE_UNSHIPPED", "SHIPPED", "DELIVERED"
    double itemTotal,
    String shippingAddress
) {}
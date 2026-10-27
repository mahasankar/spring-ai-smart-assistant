package com.enterprise.ai.assistant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record RefundRequest(
    @JsonPropertyDescription("The order ID to process the refund for")
    @JsonProperty(required = true)
    String orderId,

    @JsonPropertyDescription("The monetary amount to refund")
    @JsonProperty(required = true)
    double amount
) {}
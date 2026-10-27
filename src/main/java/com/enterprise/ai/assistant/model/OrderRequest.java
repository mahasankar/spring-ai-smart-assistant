package com.enterprise.ai.assistant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record OrderRequest(
    @JsonPropertyDescription("The unique order ID to query details for (e.g., ORD-9821)")
    @JsonProperty(required = true)
    String orderId
) {}
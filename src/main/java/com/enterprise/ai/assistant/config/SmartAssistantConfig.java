package com.enterprise.ai.assistant.config;

import com.enterprise.ai.assistant.model.OrderDetails;
import com.enterprise.ai.assistant.model.OrderRequest;
import com.enterprise.ai.assistant.model.RefundRequest;
import com.enterprise.ai.assistant.model.RefundResult;
import com.enterprise.ai.assistant.service.OrderService;
import com.enterprise.ai.assistant.service.RefundService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class SmartAssistantConfig {

    @Value("${assistant.guardrails.system-prompt}")
    private String systemGuardrailPrompt;

    @Bean
    public ChatClient supportChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem(systemGuardrailPrompt)
                .defaultFunctions("getOrderDetails", "processRefund")
                .build();
    }

    @Bean
    @Description("Fetch order status, items total, and shipping address by order ID.")
    public Function<OrderRequest, OrderDetails> getOrderDetails(OrderService orderService) {
        return request -> orderService.getOrderDetails(request.orderId());
    }

    @Bean
    @Description("Process a monetary refund for an order. Returns execution status and transaction details.")
    public Function<RefundRequest, RefundResult> processRefund(RefundService refundService) {
        return request -> refundService.processRefund(request.orderId(), request.amount());
    }
}
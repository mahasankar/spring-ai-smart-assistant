package com.enterprise.ai.assistant.service;

import com.enterprise.ai.assistant.model.RefundResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefundService {

    private static final Logger log = LoggerFactory.getLogger(RefundService.class);

    public RefundResult processRefund(String orderId, double amount) {
        log.info("[MOCK PAYMENT GATEWAY] Processing refund of ${} for orderId: {}", amount, orderId);
        
        // Basic validation mock logic
        if (amount <= 0) {
            return new RefundResult(null, orderId, false, 0.0, "Invalid refund amount requested.");
        }

        String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new RefundResult(transactionId, orderId, true, amount, "Refund successfully processed to original payment method.");
    }
}
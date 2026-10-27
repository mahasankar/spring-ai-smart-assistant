package com.enterprise.ai.assistant.service;

import com.enterprise.ai.assistant.model.OrderDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final Map<String, OrderDetails> mockOrders = new ConcurrentHashMap<>();

    public OrderService() {
        mockOrders.put("ORD-9821", new OrderDetails("ORD-9821", "IN_WAREHOUSE_UNSHIPPED", 89.99, "123 Main St, NY"));
        mockOrders.put("ORD-5542", new OrderDetails("ORD-5542", "SHIPPED", 149.50, "456 Market St, CA"));
        mockOrders.put("ORD-1100", new OrderDetails("ORD-1100", "DELIVERED", 29.99, "789 Pine St, TX"));
        mockOrders.put("ORD-9999", new OrderDetails("ORD-9999", "IN_WAREHOUSE_UNSHIPPED", 650.00, "100 Big Buck Rd, FL"));
    }

    public OrderDetails getOrderDetails(String orderId) {
        log.info("[MOCK DB] Querying order details for orderId: {}", orderId);
        return mockOrders.getOrDefault(orderId, new OrderDetails(orderId, "NOT_FOUND", 0.0, "N/A"));
    }
}
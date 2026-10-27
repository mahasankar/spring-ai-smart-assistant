package com.enterprise.ai.assistant.service;

import com.enterprise.ai.assistant.model.SupportTicketRequest;
import com.enterprise.ai.assistant.model.SupportTicketResolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SupportAssistantService {

    private static final Logger log = LoggerFactory.getLogger(SupportAssistantService.class);
    private final ChatClient chatClient;

    public SupportAssistantService(ChatClient supportChatClient) {
        this.chatClient = supportChatClient;
    }

    public String processSupportTicket(String prompt) {
        log.info("Processing standard chat prompt request...");
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    public Flux<String> streamSupportTicket(String prompt) {
        log.info("Initiating reactive stream response for user prompt...");
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }

    public SupportTicketResolution resolveTicket(SupportTicketRequest request) {
        log.info("Resolving support ticket [ID: {}, Customer: {}] via agentic tool orchestration...", 
                request.ticketId(), request.customerId());
        
        SupportTicketResolution resolution = chatClient.prompt()
                .user(spec -> spec
                        .text("""
                                Ticket ID: {ticketId}
                                Customer ID: {customerId}
                                Message: {message}
                                """)
                        .param("ticketId", request.ticketId())
                        .param("customerId", request.customerId())
                        .param("message", request.message())
                )
                .call()
                .entity(SupportTicketResolution.class);

        log.info("Successfully resolved ticket [{}] with Action: {}", 
                request.ticketId(), resolution.actionTaken());
        
        return resolution;
    }
}
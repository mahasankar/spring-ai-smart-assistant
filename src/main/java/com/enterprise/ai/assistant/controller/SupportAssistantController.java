package com.enterprise.ai.assistant.controller;

import com.enterprise.ai.assistant.model.SupportTicketRequest;
import com.enterprise.ai.assistant.model.SupportTicketResolution;
import com.enterprise.ai.assistant.service.SupportAssistantService;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/support")
public class SupportAssistantController {

    private final SupportAssistantService assistantService;

    public SupportAssistantController(SupportAssistantService assistantService) {
        this.assistantService = assistantService;
    }

    /**
     * Exposes a Server-Sent Events (SSE) streaming endpoint for real-time AI responses.
     * Example URL: http://localhost:8080/api/support/stream?message=Hello
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamSupport(@RequestParam String message) {
        return assistantService.streamSupportTicket(message);
    }
    
    /**
     * Production REST API for processing customer support tickets
     * with automated tool execution and structured JSON output.
     * URL: POST http://localhost:8080/api/support/tickets
     */
    @PostMapping(value = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
    public SupportTicketResolution resolveTicket(@Valid @RequestBody SupportTicketRequest request) {
        return assistantService.resolveTicket(request);
    }
}
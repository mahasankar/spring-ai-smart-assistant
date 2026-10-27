# System Architecture & Design Document

## 1. Problem Statement
Traditional customer support automation typically suffers from three fundamental issues:
1. **High Support Volume & Latency:** Tier-1 agents spend extensive time executing repetitive routine tasks (e.g., manual order tracking lookups, processing basic cancellations).
2. **Hallucination & Governance Risks:** Native generative AI models risk hallucinating non-existent order statuses or authorizing invalid refunds if left unconstrained.
3. **Unstructured Output Constraints:** Default conversational LLM responses produce unstructured text blocks that internal enterprise microservices cannot programmatically process or log accurately.

---

## 2. Core Architecture & Request Lifecycle

This application resolves these challenges using a deterministic **Reason-Act (ReAct)** tool orchestration loop coupled with strict system guardrails and schema validation.

```
[Client / External Service]
           │
           ├── POST /api/support/tickets
           ▼
[SupportAssistantController] ──(@Valid Request Body)──► [SupportAssistantService]
                                                              │
                                                   (Spring AI ChatClient)
                                                              │
                                                   [Gemini LLM Reasoning Engine]
                                                   /                           \
                                  (Tool Call: getOrderDetails)      (Tool Call: processRefund)
                                                 /                               \
                                        [OrderService]                    [RefundService]
                                                 \                               /
                                             (Mock Database Gateway Execution)
                                                              │
                                                              ▼
                                              [Spring AI BeanOutputConverter]
                                                              │
[Client / External Service] ◄── Structured JSON Payload (`SupportTicketResolution`)
```

---

## 3. Key Design Decisions

### A. Server-Side Context & Security Isolation
While inputs accept incoming requests, operational safety requires isolating key security attributes. In real-world enterprise extensions, `customerId` and authorization contexts are derived from secure auth tokens (e.g., OAuth2/JWT), preventing malicious users from requesting actions on third-party orders.

### B. Tool Descriptions vs. System Prompts
* **Tool Specifications (`@Description`):** Spring AI automatically parses Java function descriptions and bean input/output types into a JSON Schema provided to the model. This dictates *what* capability the model has.
* **System Prompt Guardrails:** System prompts specify operational constraints, business thresholds (e.g., $500 max automated refund limit), and mandatory sequencing (e.g., *“Always perform an order lookup before executing a cancellation”*). This dictates *how* and *when* tools may be called.

### C. Architectural Split: Streaming vs. Structured Execution
* **Streaming (`/api/support/stream`):** Leverages WebFlux `Flux<String>` and Server-Sent Events (SSE) via `.stream().content()` for real-time word-by-word UI feedback where parsing partial text strings does not break system logic.
* **Structured Action Execution (`/api/support/tickets`):** Leverages `.call().entity(...)` with Jackson deserialization to enforce strict, atomic JSON outputs required for downstream microservice integration.
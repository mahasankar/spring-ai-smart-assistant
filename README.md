# Enterprise AI Smart Assistant (`spring-ai-smart-assistant`)

A production-grade, enterprise-ready Tier-1 customer support automation engine built with **Spring Boot 3.4.2** and **Spring AI**. This service bridges unstructured customer inquiries with secure backend Java services using agentic tool routing, policy guardrails, and strongly-typed structured JSON outputs.

---

## 🚀 Key Features

* **Agentic Tool Orchestration:** Combines Gemini's reasoning capabilities with Java `Function` beans to dynamically look up orders and process refunds.
* **Policy Governance & Guardrails:** Enforces business logic safety limits (e.g., maximum automated refund thresholds of $500.00) directly via system prompt guardrails.
* **Structured Entity Parsing:** Uses Spring AI's `BeanOutputConverter` to map unstructured LLM text outputs into strongly-typed Java Records (`SupportTicketResolution`).
* **Real-Time Reactive Streaming:** Exposes Server-Sent Events (SSE) via Spring WebFlux (`Flux<String>`) for word-by-word streaming chat responses.
* **Production Hardened:** Features global exception handling (`@RestControllerAdvice`), Jakarta validation (`@Valid`), structured logging, and externalized configuration properties.

---

## 🛠️ Tech Stack

* **Java 21**
* **Spring Boot 3.4.2**
* **Spring AI (1.0.0-M5)** (OpenAI/Gemini Chat Model integrations)
* **Spring WebFlux / MVC**
* **Jakarta Validation**
* **Project Reactor**

---

## ⚙️ Configuration

Set your Gemini API key as an environment variable on your local system or within your IDE run configuration:

```bash
export GEMINI_API_KEY="your-google-ai-studio-api-key"
```

The application picks this up via `application.properties`:
```properties
spring.ai.openai.api-key=${GEMINI_API_KEY}
spring.ai.openai.chat.options.model=gemini-2.5-flash
spring.ai.openai.base-url=https://generativelanguage.googleapis.com/v1beta/openai/

# Assistant Guardrails & Custom Properties
assistant.guardrails.max-refund=500.00
assistant.guardrails.system-prompt=You are an Enterprise Automated Tier-1 Customer Support & Action Engine...
```

---

## 🏃‍♂️ Build, Run & Test

### 1. Build the Project
```bash
./mvnw clean install
```

### 2. Run the Application
```bash
./mvnw spring-boot:run
```

### 3. Test the REST Endpoints

* **Process a Support Ticket (Structured JSON Resolution):**
  ```bash
  curl -X POST "http://localhost:8080/api/support/tickets" \
  -H "Content-Type: application/json" \
  -d '{
    "ticketId": "TCK-101",
    "customerId": "CUST-501",
    "message": "Hi, my order #ORD-9821 hasn'\''t arrived. If it hasn'\''t shipped yet, please cancel it and refund $89.99."
  }'
  ```

* **Stream a Real-Time Response (SSE):**
  ```bash
  curl -N "http://localhost:8080/api/support/stream?message=Hello,%20what%20can%20you%20do?"
  ```

---

## 📖 Architecture Documentation
For an in-depth exploration of the underlying cognitive loop, data flows, and design trade-offs, refer to [ARCHITECTURE.md](ARCHITECTURE.md).
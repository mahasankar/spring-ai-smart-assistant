# 1. Start with a lightweight Java 21 runtime
FROM eclipse-temurin:21-jre-jammy

# 2. Create a directory for the app
WORKDIR /app

# 3. Copy the compiled JAR from your local target folder into the container
# Note: Ensure you run 'mvn package' before building this image
COPY target/spring-ai-smart-assistant-0.0.1-SNAPSHOT.jar spring-ai-smart-assistant-0.0.1-SNAPSHOT.jar

# 4. Tell Docker which port the app runs on
EXPOSE 8080

# 5. The command to run your service
ENTRYPOINT ["java","-jar", "spring-ai-smart-assistant-0.0.1-SNAPSHOT.jar"]
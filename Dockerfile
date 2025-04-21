# Use Alpine-based Java 21 JDK image
FROM alpine/java:21-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY ./target/embeddings-recommendation-engine-1.0.0.jar /app/app.jar

# Expose the port (default Spring Boot port is 8080, adjust if needed)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

FROM eclipse-temurin:17 as builder

# Copy necessary files for dependency resolution
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code and build the application
COPY src src
RUN ./mvnw package

# Start a new image with a smaller base image
FROM eclipse-temurin:17 as app
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /builds/snake_server.jar /app/

# Clean up unnecessary files
RUN rm -rf /src/app/.mvn/app/mvnw/app/pom.xml

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "snake_server.jar"]

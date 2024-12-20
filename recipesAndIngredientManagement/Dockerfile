# Start with an official Maven image with Java 21 to build the application
FROM maven:3.9.8-eclipse-temurin-21 as build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .

# Copy the rest of the application source code and build the application
COPY src ./src
RUN mvn clean install -DskipTests

# Start a new image for the runtime with only the JRE
FROM eclipse-temurin:21-jre

# Set the working directory in the runtime image
WORKDIR /app

# Copy the built application from the Maven build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (optional, commonly 8080 for Spring Boot apps)
EXPOSE 3000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
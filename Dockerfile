# Stage 1: Build the application using Maven
FROM maven:3.8-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the application, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Create the final, smaller runtime image
FROM openjdk:17-jre-slim

WORKDIR /app

# Copy the built JAR file from the 'build' stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]
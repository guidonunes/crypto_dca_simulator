# STAGE 1: Build the application
# We use a Maven image with Java 21 to compile the code
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only the pom.xml first (to cache dependencies)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the jar
COPY src ./src
RUN mvn clean package -DskipTests

# STAGE 2: Run the application
# We use a lightweight Java 21 image for the final container
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (where Spring Boot runs)
EXPOSE 8080

# The command to start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
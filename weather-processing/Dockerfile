# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy all POM files to resolve dependencies before copying source code
COPY pom.xml .
COPY weather-shared/pom.xml ./weather-shared/
COPY weather-gateway/pom.xml ./weather-gateway/
COPY weather-processing/pom.xml ./weather-processing/

# Download dependencies
RUN mvn -B dependency:go-offline

# Copy the rest of the source code
COPY . .

# Build the project
RUN mvn -B package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/weather-processing/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
# Multi-stage build for Authentication
FROM maven:3.9-eclipse-temurin-25 AS build

# Set working directory
WORKDIR /build

# Copy the parent pom.xml
COPY pom.xml .

# Copy all module pom.xml files (required by parent POM)
COPY Authentication/pom.xml Authentication/pom.xml
COPY UserManagement/pom.xml UserManagement/pom.xml
COPY EurikaServer/pom.xml EurikaServer/pom.xml
COPY ApiGateway/pom.xml ApiGateway/pom.xml
COPY CarsManagement/pom.xml CarsManagement/pom.xml
COPY FrontEnd/pom.xml FrontEnd/pom.xml
COPY OffersManagement/pom.xml OffersManagement/pom.xml

# Copy only Authentication source code
COPY Authentication/src Authentication/src

# Build only the Authentication module
RUN mvn clean package -DskipTests -pl Authentication -am

# Stage 2: Run the application
FROM eclipse-temurin:25-jre-alpine

# Set working directory
WORKDIR /app

# Copy the JAR file from build stage
COPY --from=build /build/Authentication/target/*.jar authentication.jar

# Expose Authentication port
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS=""
ENV EUREKA_URL="http://localhost:8761/eureka/"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar authentication.jar"]

FROM maven:3.9.11-eclipse-temurin-25-alpine AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:25-jre-alpine AS final
RUN apk add curl
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8761

CMD ["java","-jar","app.jar"]

# Run service command
# docker run --name eureka-service --network microservicearchitectureoverviewtask_default -p 8761:8761 eureka-service
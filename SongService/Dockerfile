FROM maven:3.9.11-eclipse-temurin-25-alpine AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:25-jre-alpine AS final
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8082

CMD ["java","-jar","app.jar"]

# Run service command
# docker run --name song-service --network microservicearchitectureoverviewtask_default -p 8082:8082 song-service
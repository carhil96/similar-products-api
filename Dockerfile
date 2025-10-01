FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /your_app

ARG SKIP_TESTS=false

COPY . .

RUN if [ "$SKIP_TESTS" = "true" ]; then \
        mvn clean package -DskipTests; \
    else \
        mvn clean verify; \
    fi

FROM eclipse-temurin:21-jre-alpine
WORKDIR /your_app

COPY --from=build /your_app/target/*.jar your_app.jar

EXPOSE 5001

HEALTHCHECK --interval=30s --timeout=5s \
  CMD curl -f http://localhost:5001/actuator/health || exit 1

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","/your_app/your_app.jar"]

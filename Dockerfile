# Etapa 1: build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /yourApp

# Copiar proyecto y compilar
COPY . .
RUN mvn clean package -DskipTests


# Etapa 2: runtime
FROM eclipse-temurin:21-jre-ubi9-minimal

# Copiar solo el jar desde la etapa de build
COPY --from=build /yourApp/target/*.jar yourApp.jar

# Exponer puerto de Spring Boot
EXPOSE ${APP_PORT}

# Healthcheck para asegurar que el contenedor está vivo
HEALTHCHECK --interval=30s --timeout=5s CMD curl -f http://localhost:${APP_PORT}/actuator/health || exit 1

# Comando de arranque
ENTRYPOINT ["java","-jar","/yourApp.jar"]

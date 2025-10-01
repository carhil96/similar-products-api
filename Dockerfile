# =========================
# Etapa 1: Build & Test
# =========================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /your_app

# Argumento opcional para saltar tests
ARG SKIP_TESTS=false

# Copiar proyecto completo
COPY . .

# Ejecutar tests si SKIP_TESTS=false, o solo empaquetar si SKIP_TESTS=true
RUN if [ "$SKIP_TESTS" = "true" ]; then \
        mvn clean package -DskipTests; \
    else \
        mvn clean verify; \
    fi

# =========================
# Etapa 2: Runtime
# =========================
FROM eclipse-temurin:21-jre-ubi9-minimal
WORKDIR /your_app

# Copiar solo el JAR desde el stage build
COPY --from=build /your_app/target/*.jar your_app.jar

# Exponer puerto de Spring Boot
EXPOSE ${APP_PORT}

# Healthcheck para asegurar que el contenedor está vivo
HEALTHCHECK --interval=30s --timeout=5s \
  CMD curl -f http://localhost:${APP_PORT}/actuator/health || exit 1

# Comando de arranque
ENTRYPOINT ["java","-jar","/your_app/your_app.jar"]

FROM eclipse-temurin:17.0.6_10-jre as layers
WORKDIR /app
COPY target/congestion-tax-calculator-service.jar congestion-tax-calculator-service.jar

RUN mkdir /app/extracted && \
    java -Djarmode=layertools -jar /app/congestion-tax-calculator-service.jar extract --destination /app/extracted

FROM gcr.io/distroless/java17
WORKDIR /app
COPY --from=layers /app/extracted/dependencies /app/
COPY --from=layers /app/extracted/spring-boot-loader /app/
COPY --from=layers /app/extracted/snapshot-dependencies /app/
COPY --from=layers /app/extracted/application /app/

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
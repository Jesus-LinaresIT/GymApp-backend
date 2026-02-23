# Etapa 1: Construcción (Build)
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
# Copiamos solo el pom.xml primero para aprovechar el caché de dependencias de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copiamos el código fuente y compilamos omitiendo los tests para mayor velocidad
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Run)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar
# Exponemos el puerto dinámico
EXPOSE 8080
# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
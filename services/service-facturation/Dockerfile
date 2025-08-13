# Étape 1 : builder l'appli avec Maven (image avec Maven et JDK)
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copier pom.xml et télécharger les dépendances (cache)
COPY pom.xml .

RUN mvn dependency:go-offline

# Copier le reste des sources
COPY src ./src

# Builder l'application (génère un jar)
RUN mvn clean package -DskipTests

# Étape 2 : image runtime légère avec juste le JDK
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier le jar depuis la build
COPY --from=build /app/target/service-facturation-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port sur lequel Spring Boot tourne (par défaut 8080)
EXPOSE 8080

# Commande pour lancer l'appli
ENTRYPOINT ["java", "-jar", "app.jar"]

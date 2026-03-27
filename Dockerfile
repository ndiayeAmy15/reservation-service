# 1. Prend une image Java 17 légère comme base
FROM amazoncorretto:17-alpine

VOLUME /tmp
# 2. Définit le dossier de travail dans le conteneur
WORKDIR /app

# 3. Copie votre .jar (produit par Maven) dans le conteneur
COPY *.jar reservationservice.jar

# 4. Indique que l'application écoute sur le port 8080
EXPOSE 8082

# 5. Commande lancée au démarrage du conteneur
ENTRYPOINT ["java", "-jar", "reservationservice.jar"]
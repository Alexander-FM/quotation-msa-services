FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./target/appmic-quotingplugins-catalogs-1.0.0-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "appmic-quotingplugins-catalogs-1.0.0-SNAPSHOT.jar"]
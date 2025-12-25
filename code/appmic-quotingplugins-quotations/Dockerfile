FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./target/appmic-quotingplugins-quotations-1.0.0-SNAPSHOT.jar .

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "appmic-quotingplugins-quotations-1.0.0-SNAPSHOT.jar"]
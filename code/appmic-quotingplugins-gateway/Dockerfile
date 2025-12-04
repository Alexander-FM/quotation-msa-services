FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./target/appmic-quotingplugins-gateway-1.0.0-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "appmic-quotingplugins-gateway-1.0.0-SNAPSHOT.jar"]
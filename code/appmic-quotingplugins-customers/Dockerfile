FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./target/appmic-quotingplugins-customers-1.0.0-SNAPSHOT.jar .

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "appmic-quotingplugins-customers-1.0.0-SNAPSHOT.jar"]
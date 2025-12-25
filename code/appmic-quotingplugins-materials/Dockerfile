FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./target/appmic-quotingplugins-materials-1.0.0-SNAPSHOT.jar .

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "appmic-quotingplugins-materials-1.0.0-SNAPSHOT.jar"]
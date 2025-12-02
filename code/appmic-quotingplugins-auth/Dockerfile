FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./target/appmic-quotingplugins-auth-1.0.0-SNAPSHOT.jar .

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "appmic-quotingplugins-auth-1.0.0-SNAPSHOT.jar"]
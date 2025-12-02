FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./target/appmic-quotingplugins-employees-1.0.0-SNAPSHOT.jar .

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "appmic-quotingplugins-employees-1.0.0-SNAPSHOT.jar"]
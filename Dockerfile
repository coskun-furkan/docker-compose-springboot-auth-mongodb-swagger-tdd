FROM openjdk:23-jdk-slim

ENV APP_HOME=/app
WORKDIR $APP_HOME

COPY target/tutorials-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
EXPOSE 27017

ENTRYPOINT ["java", "-jar", "app.jar"]


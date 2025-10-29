
FROM openjdk:21-jdk-alpine

VOLUME /tmp

ARG JAR_FILE=target/payment-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

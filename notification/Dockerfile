# Use official OpenJDK image
FROM openjdk:17-jdk-alpine

# Create a volume to store temporary files
VOLUME /tmp

# Define build argument to allow dynamic JAR name
ARG JAR_FILE=target/rating-0.0.1-SNAPSHOT.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} app.jar

# Define the command to run the JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]

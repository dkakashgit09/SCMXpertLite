# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at /app
COPY target/SCMXpertLite_Project-0.0.1-SNAPSHOT.jar /app/SCMXpertLite_Project-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 7077

# Run the JAR file
CMD ["java", "-jar", "/app/SCMXpertLite_Project-0.0.1-SNAPSHOT.jar"]

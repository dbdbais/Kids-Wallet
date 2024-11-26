# Step 1: Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from the host machine to the container
COPY build/libs/*.jar app.jar

# Step 4: Expose the port your Spring Boot application runs on
EXPOSE 8000

# Step 5: Define the command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
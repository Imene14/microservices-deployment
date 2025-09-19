FROM openjdk:21-jdk-slim
LABEL authors="imenz"
WORKDIR /app
COPY target/ecommerce-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8090


ENTRYPOINT ["java", "-jar", "app.jar"]
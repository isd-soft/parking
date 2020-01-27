FROM openjdk:8-jdk-alpine
FROM maven:alpine

VOLUME /tmp
EXPOSE 8082
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
WORKDIR /app
ADD pom.xml /app
COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests
ADD target/docker-spring-boot.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]

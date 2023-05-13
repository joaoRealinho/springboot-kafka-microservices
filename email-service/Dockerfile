FROM openjdk:17-jdk-alpine
WORKDIR /build
EXPOSE 8082
COPY target/*.jar /build/email-service.jar
CMD java -jar email-service.jar
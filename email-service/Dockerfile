FROM openjdk:17-jdk-alpine
WORKDIR /build
EXPOSE 8082
ADD: ${JAR_FILE} app.jar
CMD java -jar email-service.jar
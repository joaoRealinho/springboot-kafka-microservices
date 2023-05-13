FROM openjdk:17-jdk-alpine
WORKDIR /build
EXPOSE 8081
COPY target/*.jar /build/stock-service.jar
CMD java -jar stock-service.jar
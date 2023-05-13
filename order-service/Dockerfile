FROM openjdk:17-jdk-alpine
WORKDIR /build
EXPOSE 8080
COPY target/*.jar /build/order-service.jar
CMD java -jar order-service.jar
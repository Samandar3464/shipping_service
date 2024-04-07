FROM openjdk:17-alpine
WORKDIR /app
COPY target/shippingService-0.0.1.jar /app/shippingService.jar
EXPOSE 8080
CMD ["java", "-jar", "shippingService.jar"]

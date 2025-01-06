FROM openjdk:21-jdk
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9093
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.profiles.active=docker"]
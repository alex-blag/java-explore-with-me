FROM amazoncorretto:11-alpine-jdk
COPY ["target/*.jar", "ewm-stats-server.jar"]
ENTRYPOINT ["java", "-jar", "/ewm-stats-server.jar"]

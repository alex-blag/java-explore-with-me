FROM amazoncorretto:11-alpine-jdk
COPY ["target/*.jar", "app.jar"]
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]

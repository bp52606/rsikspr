FROM openjdk:19 as runner
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar" ]
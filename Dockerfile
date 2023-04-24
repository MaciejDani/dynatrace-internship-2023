FROM openjdk:17-alpine
WORKDIR /internshipdynatrace
COPY target/nbp-api-0.0.1-SNAPSHOT.jar /internshipdynatrace/nbp-api.jar
ENTRYPOINT ["java", "-jar", "nbp-api.jar"]
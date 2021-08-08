FROM openjdk:8-jdk-alpine
MAINTAINER canh-labs.com
COPY target/shorten-1.0-SNAPSHOT.jar shorten-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/shorten-1.0-SNAPSHOT.jar"]